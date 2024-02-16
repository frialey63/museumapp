package org.pjp.museum.ui.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.pjp.museum.Env;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public final class QrCodeUtils {

    public static final String QRCODE_DIR = Env.MUSEUM_DATA + "data/qrcode";

    private static final Logger LOGGER = LoggerFactory.getLogger(QrCodeUtils.class);

    private static final String CHARSET = "UTF-8";

    private static final int SIZE = 200;

    @SuppressWarnings("deprecation")
    private static void createQR(String data, File path, String charset, Map<EncodeHintType, ErrorCorrectionLevel> hashMap, int height, int width)
            throws WriterException, IOException {

        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height);

        MatrixToImageWriter.writeToFile(matrix, FileUtils.getExtension(path), path);
    }

    private static void createLabelledQR(String data, String title, File path, String charset, Map<EncodeHintType, ErrorCorrectionLevel> hashMap, int height, int width, int fontSize)
            throws WriterException, IOException {

        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height);

        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(matrix, new MatrixToImageConfig());

        BufferedImage image = new BufferedImage(width, (height + fontSize), BufferedImage.TYPE_BYTE_BINARY);

        Font font = new Font("Arial", Font.BOLD, fontSize);

        Graphics2D g2d = (Graphics2D) image.getGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        g2d.setBackground(Color.WHITE);
        g2d.setColor(Color.BLACK);
        g2d.setFont(font);

        FontMetrics fontMetrics = g2d.getFontMetrics();
        Rectangle2D stringBounds = fontMetrics.getStringBounds(title, g2d);

        g2d.clearRect(0, 0, width, (height + fontSize));
        g2d.drawImage(bufferedImage, 0, 0, null);
        g2d.drawString(title, (int) (width / 2.0 - stringBounds.getWidth() / 2.0), height);
        //g2d.drawRect(0, 0, (width - 2), (height + fontSize - 2));

        String format = FileUtils.getExtension(path);

        if (!ImageIO.write(image, format, path)) {
            throw new IOException("Could not write an image of format " + format + " to " + path);
        }
    }

    public static boolean createAndWriteQR(String data, String filename) {
        File path = new File(QRCODE_DIR, filename);

        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        boolean result = false;

        try {
            createQR(data, path, CHARSET, hashMap, SIZE, SIZE);
            result = true;
        } catch (WriterException e) {
            LOGGER.error("failed to create QR code", e);
        } catch (IOException e) {
            LOGGER.error("failed to write QR code", e);
        }

        return result;
    }

    public static boolean createAndWriteQR(String data, String title, File dir, String filename, int size, int fontSize) {
        File path = new File(dir, filename);

        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        boolean result = false;

        try {
            createLabelledQR(data, title, path, CHARSET, hashMap, size, size, fontSize);
            result = true;
        } catch (WriterException e) {
            LOGGER.error("failed to create QR code", e);
        } catch (IOException e) {
            LOGGER.error("failed to write QR code", e);
        }

        return result;
    }

    private QrCodeUtils() {
        // prevent instantiation
    }
}
