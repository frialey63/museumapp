package org.pjp.museum.ui.view.exhibit;

import java.nio.ByteBuffer;

import org.pjp.museum.model.Exhibit;
import org.pjp.museum.service.ExhibitService;
import org.pjp.museum.ui.component.CompactVerticalLayout;
import org.pjp.museum.ui.util.AudioUtils;
import org.pjp.museum.ui.util.ImageUtils;
import org.pjp.museum.ui.util.SettingsUtil;
import org.pjp.museum.ui.view.MainLayout;
import org.pjp.museum.ui.view.number.TailNumberView;
import org.pjp.museum.ui.view.scan.ScannerZxingView;
import org.vaadin.addon.audio.server.AudioPlayer;
import org.vaadin.addon.audio.server.Stream;
import org.vaadin.addon.audio.server.encoders.WaveEncoder;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.Scroller.ScrollDirection;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

@PageTitle("Exhibit")
@Route(value = "exhibit", layout = MainLayout.class)
public class ExhibitView extends VerticalLayout implements AfterNavigationObserver, HasUrlParameter<String> {

    private static final long serialVersionUID = -3241685802423500738L;

    private static final String SORRY_AN_ERROR = "Sorry, an error occurred while attempting to look-up the exhibit for this QR Code.";

    private static final String NOT_FOUND = "not-found.jpg";

	private static String formatNameAndTailNumber(Exhibit exhibit) {
		String tailNumber = exhibit.getTailNumber();
		
		if (Exhibit.MUSEUM.equals(tailNumber)) {
			return exhibit.getName();
		}
		
		return String.format("%s<br/>%s", exhibit.getName(), tailNumber);
	}

    private final H2 title = new H2();

    private final Image image = new Image();

    private final Paragraph description = new Paragraph();

    private final PlayerControls playerControls = new PlayerControls();

    private final ExhibitService service;

    private String tailNumber;

    public ExhibitView(ExhibitService service) {
        super();
        this.service = service;

        Button nextButton = new Button("Go To Next Exhibit", e -> {
            if (SettingsUtil.isScanning()) {
                UI.getCurrent().navigate(ScannerZxingView.class);
            } else {
                UI.getCurrent().navigate(TailNumberView.class);
            }
        });
        
        Details details = new Details("Description", description);
        details.setOpened(false);

        VerticalLayout vl = new CompactVerticalLayout(title, image, details, playerControls, nextButton);

        vl.setHorizontalComponentAlignment(Alignment.CENTER, title, image, playerControls, nextButton);
        vl.setHorizontalComponentAlignment(Alignment.START, details);

        Scroller scroller = new Scroller();
        scroller.setScrollDirection(ScrollDirection.VERTICAL);
        scroller.setContent(vl);

        add(scroller);

        setHeightFull();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        service.getExhibit(tailNumber).ifPresentOrElse(exhibit -> {
            title.getElement().setProperty("innerHTML", formatNameAndTailNumber(exhibit));
            title.getStyle().set("text-align", "center");

            description.getElement().setProperty("innerHTML", exhibit.getDescription());

            String imageFile = exhibit.getImageFile();
            StreamResource imageResource = new StreamResource(imageFile, () -> ImageUtils.getInputStreamFromFile(imageFile));

            image.setSrc(imageResource);
            image.setWidth("80%");

            String audioFile = exhibit.getAudioFile();

            // decodeToPcm method found in demo
            ByteBuffer fileBytes = AudioUtils.decodeToPcm(audioFile);
            // createWaveStream method found in demo
            Stream stream = AudioUtils.createWaveStream(fileBytes, new WaveEncoder());

            AudioPlayer player = new AudioPlayer(stream);

            playerControls.setPlayer(player, audioFile);
            playerControls.initPositionSlider();
        }, () -> {

            description.getElement().setProperty("innerHTML", SORRY_AN_ERROR);

            StreamResource imageResource = new StreamResource(NOT_FOUND, () -> ImageUtils.getInputStreamFromFile(NOT_FOUND));

            image.setSrc(imageResource);
            image.setWidth("80%");

            playerControls.setEnabled(false);
        });
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        tailNumber = parameter;
    }

}
