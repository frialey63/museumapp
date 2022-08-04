package org.pjp.museum.views.exhibit;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.pjp.museum.views.MainLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addon.audio.server.AudioPlayer;
import org.vaadin.addon.audio.server.Encoder;
import org.vaadin.addon.audio.server.Stream;
import org.vaadin.addon.audio.server.encoders.WaveEncoder;
import org.vaadin.addon.audio.server.util.ULawUtil;
import org.vaadin.addon.audio.server.util.WaveUtil;
import org.vaadin.addon.audio.shared.PCMFormat;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ExhibitView.class);

    private static InputStream getInputStreamFromFile(String filename) throws RuntimeException {
        InputStream is = null;

        try {
            is = new FileInputStream(DATA_DIR + File.separator + filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return is;
    }

    /**
     * Returns a ByteBuffer filled with PCM data. If the original audio file is using
     * a different encoding, this method attempts to decode it into PCM signed data.
     *
     * @param fname
     *            filename
     * @param dir
     *            directory in which the file exists
     * @return ByteBuffer containing byte[] of PCM data
     */
    private static ByteBuffer decodeToPcm(String fname, String dir) {
        // TODO: add other supported encodings for decoding to PCM
        ByteBuffer buffer = null;
        try {
            // load audio file
            Path path = Paths.get(dir + "/" + fname);
            byte[] bytes = Files.readAllBytes(path);

            // create input stream with audio file bytes
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(bytes));
            AudioFormat.Encoding encoding = audioInputStream.getFormat().getEncoding();

            // handle current encoding
            if (encoding.equals(AudioFormat.Encoding.ULAW)) {
                buffer = ULawUtil.decodeULawToPcm(audioInputStream);
            } else {
                // for now assume it is PCM data and load it straight into byte buffer
                buffer = ByteBuffer.wrap(bytes);
            }

        } catch (UnsupportedAudioFileException e) {
            Notification.show("Audio file is not of supported type");
        } catch (Exception e) {
            LOGGER.error("File read failed");
            e.printStackTrace();
        }

        return buffer;
    }

    private static Stream createWaveStream(ByteBuffer waveFile, Encoder outputEncoder) {
        int startOffset = WaveUtil.getDataStartOffset(waveFile);
        int dataLength = WaveUtil.getDataLength(waveFile);
        int chunkLength = 5000;

        PCMFormat dataFormat = WaveUtil.getDataFormat(waveFile);

        LOGGER.debug(dataFormat.toString());
        LOGGER.debug("arrayLength: " + waveFile.array().length + "\n\rstartOffset: " + startOffset + "\n\rdataLength: " + dataLength + "\r\nsampleRate: " + dataFormat.getSampleRate());
        LOGGER.debug("arrayLength: {}\n\rstartOffset: {}\n\rdataLength: {}\r\nsampleRate: {}", waveFile.array().length, startOffset, dataLength, dataFormat.getSampleRate());

        ByteBuffer dataBuffer = ByteBuffer.wrap(waveFile.array(), startOffset, dataLength);
        Stream stream = new Stream(dataBuffer.slice(), dataFormat, outputEncoder, chunkLength);

        return stream;
    }

    private final Image image = new Image();

    private final PlayerControls playerControls = new PlayerControls();

    public ExhibitView() {
        super();

        // TODO header and title

        // TODO paragraph and description

        setHorizontalComponentAlignment(Alignment.CENTER, image);

        add(image, playerControls);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        // TODO get Exhibit ID from the query parameters

        String imageFile = "IMG_20220622_151008797.jpg";

        StreamResource imageResource = new StreamResource(imageFile, () -> getInputStreamFromFile(imageFile));

        image.setSrc(imageResource);
        image.setWidth("600px");

        String audioFile = "ABCSample.wav";

        // decodeToPcm method found in demo
        ByteBuffer fileBytes = decodeToPcm(audioFile, DATA_DIR);

        // createWaveStream method found in demo
        Stream stream = createWaveStream(fileBytes, new WaveEncoder());

        AudioPlayer player = new AudioPlayer(stream);

        playerControls.setPlayer(player, audioFile);
        playerControls.initPositionSlider();
    }

}
