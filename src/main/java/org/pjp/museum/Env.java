package org.pjp.museum;

import java.io.File;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Env {

    private static final Logger LOGGER = LoggerFactory.getLogger(Env.class);

    public static String MUSEUM_DATA;

    static {
        String museumData = System.getenv("MUSEUM_DATA");

        LOGGER.info("MUSEUM_DATA = {}", museumData);

        MUSEUM_DATA = Strings.isBlank(museumData) ? "" : (museumData + File.separator);
    }

    private Env() {
        // prevent instantiation
    }
}
