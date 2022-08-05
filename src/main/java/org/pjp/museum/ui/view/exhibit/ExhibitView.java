package org.pjp.museum.ui.view.exhibit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.pjp.museum.ui.util.AudioUtils;
import org.pjp.museum.ui.view.MainLayout;
import org.vaadin.addon.audio.server.AudioPlayer;
import org.vaadin.addon.audio.server.Stream;
import org.vaadin.addon.audio.server.encoders.WaveEncoder;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

@PageTitle("Exhibit")
@Route(value = "exhibit", layout = MainLayout.class)
public class ExhibitView extends VerticalLayout implements AfterNavigationObserver {

    private static final long serialVersionUID = -3241685802423500738L;

    private static final String DATA_DIR = "data";

    private static InputStream getInputStreamFromFile(String filename) throws RuntimeException {
        InputStream is = null;

        try {
            is = new FileInputStream(DATA_DIR + File.separator + filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return is;
    }

    private final H2 title = new H2();

    private final Image image = new Image();

    private final Paragraph description = new Paragraph();

    private final PlayerControls playerControls = new PlayerControls();

    public ExhibitView() {
        super();

        setHorizontalComponentAlignment(Alignment.CENTER, title, image, playerControls);

        setHorizontalComponentAlignment(Alignment.START, description);

        add(title, image, description, playerControls);
    }

    // TODO get Exhibit ID from the query parameters

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        title.getElement().setProperty("innerHTML", "Blackburn Buccaneer S2<br>XV352");
        title.getStyle().set("text-align", "center");

        description.getElement().setProperty("innerHTML", "The Buccaneer was originally designed as a Maritime Strike aircraft for the Royal Navy, under the requirement designation NA.39.<br>" +
                "Later adopted by the Royal Air Force, the Buccaneer had a successful career, culminating with participation in the Gulf War.");

        String imageFile = "IMG_20220622_151008797.jpg";

        StreamResource imageResource = new StreamResource(imageFile, () -> getInputStreamFromFile(imageFile));

        image.setSrc(imageResource);
        image.setWidth("80%");

        String audioFile = "ABCSample.wav";

        // decodeToPcm method found in demo
        ByteBuffer fileBytes = AudioUtils.decodeToPcm(audioFile, DATA_DIR);

        // createWaveStream method found in demo
        Stream stream = AudioUtils.createWaveStream(fileBytes, new WaveEncoder());

        AudioPlayer player = new AudioPlayer(stream);

        playerControls.setPlayer(player, audioFile);
        playerControls.initPositionSlider();
    }

}
