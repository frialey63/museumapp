package org.pjp.museum.ui.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.pjp.museum.Env;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public final class QrCodeUtils {

    public static final String QRCODE_DIR = Env.MUSEUM_DATA + "data/qrcode";

    private static final Logger LOGGER = LoggerFactory.getLogger(QrCodeUtils.class);

    private static final String CHARSET = "UTF-8";

    private static final int SIZE = 200;

    @SuppressWarnings("deprecation")
    private static void createQR(String data, String path, String charset, Map<EncodeHintType, ErrorCorrectionLevel> hashMap, int height, int width)
            throws WriterException, IOException {

        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset),
                BarcodeFormat.QR_CODE, width, height);

        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }

    public static boolean createAndWriteQR(String data, String filename) {
        String path = QRCODE_DIR + File.separator + filename;

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

    private QrCodeUtils() {
        // prevent instantiation
    }
}
