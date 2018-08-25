package cms.utils;

import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.Properties;

/**
 * @ClassName Constants
 * @Author MaZhuli
 * @Date 2018/8/25 15:55
 * @Description 系统常量
 * @Version 1.0
 **/
public class Constants {
    public static final String SECRET_KEY;

    static {
        Properties properties = new Properties();
        InputStream in = Constants.class.getClassLoader().getResourceAsStream("constants.properties");
        try {
            properties.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SECRET_KEY = properties.getProperty("SECRET_KEY");
    }
}
