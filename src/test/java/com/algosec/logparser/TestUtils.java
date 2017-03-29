package com.algosec.logparser;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TestUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestUtils.class);


    private TestUtils() {
    }


    public static String convertRelativePathToFileToAbsolute(String relative) {
        URL url = ClassLoader.getSystemResource(relative);

        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return file.getAbsolutePath();
    }

}
