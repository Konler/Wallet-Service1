package ru.ylab.task1.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    public static Properties getProperties() throws FileNotFoundException {
        Properties properties = new Properties();
        try {
            ClassLoader classLoader = PropertiesUtil.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("config.properties");

            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException();
            }
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
        return properties;
    }

}
