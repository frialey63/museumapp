package org.pjp.museum.ui.util;

import java.io.File;

import org.pjp.museum.Env;

public final class TextUtils {

    private static final String TEXT_DIR = Env.MUSEUM_DATA + "data/text";

    public static boolean existsAndReadable(String filename) {
    	File file = new File(TEXT_DIR, filename);
		return file.exists() && file.canRead();
    }

    private TextUtils() {
        // prevent instantiation
    }

}
