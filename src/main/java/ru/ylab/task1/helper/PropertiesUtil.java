package ru.ylab.task1.helper;

import ru.ylab.task1.exception.DbException;

import javax.servlet.ServletContext;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class PropertiesUtil {

    public static Properties getProperties(ServletContext context) throws FileNotFoundException, DbException {
        Properties properties = new Properties();
        String configPath = "C:\\Users\\Elizaveta\\OneDrive\\Рабочий стол\\Прога\\Java 2 сем\\Wallet-Service\\src\\main\\webapp\\WEB-INF\\config.properties";
        try {
            InputStream inputStream = Files.newInputStream(Path.of(configPath));
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
