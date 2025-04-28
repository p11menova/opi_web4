package com.example.web4_2.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

public class PropertiesLoader {
    private static final Properties properties = new Properties();

    static {
        try (InputStreamReader reader = new InputStreamReader(
                Objects.requireNonNull(PropertiesLoader.class.getClassLoader()
                        .getResourceAsStream("application-test.properties")),
                StandardCharsets.UTF_8)) {
            properties.load(reader);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось загрузить application-test.properties", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}


