package utils;

import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class Constants {
    public static final String SECRET_KEY;

    static {
        Properties properties = new Properties();
        InputStream in = Constants.class.getClassLoader().getResourceAsStream("constants.properties");
        try {
            properties.load(in);
        } catch (Exception e) {
            System.out.println("未找到配置文件!");
        }
        SECRET_KEY = properties.getProperty("SECRET_KEY");
    }
}
