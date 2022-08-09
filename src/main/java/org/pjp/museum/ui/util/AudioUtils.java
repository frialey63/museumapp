package org.pjp.museum.ui.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addon.audio.server.Encoder;
import org.vaadin.addon.audio.server.Stream;
import org.vaadin.addon.audio.server.util.ULawUtil;
import org.vaadin.addon.audio.server.util.WaveUtil;
import org.vaadin.addon.audio.shared.PCMFormat;

import com.vaadin.flow.component.notification.Notification;

public final class AudioUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(AudioUtils.class);

    private static final String AUDIO_DIR = "data/audio";

    /**
     * Returns a ByteBuffer filled with PCM data. If the original audio file is using
     * a different encoding, this method attempts to decode it into PCM signed data.
     *
     * @param fname
     *            filename
     * @return ByteBuffer containing byte[] of PCM data
     */
    public static ByteBuffer decodeToPcm(String fname) {
        // TODO: add other supported encodings for decoding to PCM
        ByteBuffer buffer = null;
        try {
            // load audio file
            Path path = Paths.get(AUDIO_DIR + File.separator + fname);
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

    public static Stream createWaveStream(ByteBuffer waveFile, Encoder outputEncoder) {
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

    private AudioUtils() {
        // prevent instantiation
    }
}
