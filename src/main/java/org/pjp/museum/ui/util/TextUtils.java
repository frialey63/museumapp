package org.pjp.museum.ui.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.pjp.museum.Env;

public final class TextUtils {

    private static final String TEXT_DIR = Env.MUSEUM_DATA + "data/text";

    public static String readText(String filename) throws RuntimeException {
        File file = new File(TEXT_DIR, filename);

        try {
            return Files.readString(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean existsAndReadable(String filename) {
        File file = new File(TEXT_DIR, filename);
        return file.exists() && file.canRead();
    }

    private TextUtils() {
        // prevent instantiation
    }

}
