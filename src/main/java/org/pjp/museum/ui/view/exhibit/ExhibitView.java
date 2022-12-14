package org.pjp.museum.ui.view.exhibit;

import java.nio.ByteBuffer;

import org.pjp.museum.service.ExhibitService;
import org.pjp.museum.ui.component.CompactVerticalLayout;
import org.pjp.museum.ui.util.AudioUtils;
import org.pjp.museum.ui.util.ImageUtils;
import org.pjp.museum.ui.util.SettingsUtil;
import org.pjp.museum.ui.view.MainLayout;
import org.pjp.museum.ui.view.number.TailNumberView;
import org.pjp.museum.ui.view.scan.ScannerView;
import org.vaadin.addon.audio.server.AudioPlayer;
import org.vaadin.addon.audio.server.Stream;
import org.vaadin.addon.audio.server.encoders.WaveEncoder;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
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

    private final H2 title = new H2();

    private final Image image = new Image();

    private final Paragraph description = new Paragraph();

    private final PlayerControls playerControls = new PlayerControls();

    private final ExhibitService service;

    private String uuid;

    public ExhibitView(ExhibitService service) {
        super();
        this.service = service;

        Button nextButton = new Button("Goto Next Exhibit", e -> {
            if (SettingsUtil.isScanning()) {
                UI.getCurrent().navigate(ScannerView.class);
            } else {
                UI.getCurrent().navigate(TailNumberView.class);
            }
        });

        VerticalLayout vl = new CompactVerticalLayout(title, image, description, playerControls, nextButton);

        vl.setHorizontalComponentAlignment(Alignment.CENTER, title, image, playerControls, nextButton);
        vl.setHorizontalComponentAlignment(Alignment.START, description);

        Scroller scroller = new Scroller();
        scroller.setScrollDirection(ScrollDirection.VERTICAL);
        scroller.setContent(vl);

        add(scroller);

        setHeightFull();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        service.getExhibit(uuid).ifPresent(exhibit -> {
            title.getElement().setProperty("innerHTML", String.format("%s<br/>%s", exhibit.getName(), exhibit.getTailNumber()));
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
        });
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        uuid = parameter;
    }

}
