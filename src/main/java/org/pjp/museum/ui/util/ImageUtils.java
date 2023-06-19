package org.pjp.museum.ui.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.pjp.museum.Env;

public final class ImageUtils {

    private static final String IMAGE_DIR = Env.MUSEUM_DATA + "data/image";

    public static InputStream getInputStreamFromFile(String filename) throws RuntimeException {
        try {
        	return new FileInputStream(IMAGE_DIR + File.separator + filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static boolean existsAndReadable(String filename) {
    	File file = new File(IMAGE_DIR, filename);
		return file.exists() && file.canRead();
    }

    private ImageUtils() {
        // prevent instantiation
    }

}
