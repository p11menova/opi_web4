package com.example.web4_2.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = PropertiesLoader.class.getClassLoader()
                .getResourceAsStream("application-test.properties")) {
            if (input == null) {
                throw new RuntimeException("Не найден application-test.properties в classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке application-test.properties", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}


