package com.phptravels.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("src/test/resources/config/config.properties")) {
            PROPERTIES.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load config.properties", e);
        }
    }

    private ConfigReader() {
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }
}
