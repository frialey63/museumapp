package org.pjp.museum.ui.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public final class ImageUtils {

    private static final String IMAGE_DIR = "data/image";

    public static InputStream getInputStreamFromFile(String filename) throws RuntimeException {
        InputStream is = null;

        try {
            is = new FileInputStream(IMAGE_DIR + File.separator + filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return is;
    }

    private ImageUtils() {
        // prevent instantiation
    }

}
