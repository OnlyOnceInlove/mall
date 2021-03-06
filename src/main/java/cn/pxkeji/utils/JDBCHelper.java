package cn.pxkeji.utils;

import org.springframework.beans.factory.annotation.Value;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

/**
 * @ClassName jdbcHelper
 * @Author MaZhuli
 * @Date 2018/8/28 10:08
 * @Description jdbc连接
 * @Version 1.0
 **/
public class JDBCHelper {
    @Value("${spring.datasource.druid.url}")
    public static String url;
    @Value("${spring.datasource.druid.driver-class-name}")
    public static String name;
    @Value("${spring.datasource.druid.username}")
    public static String user;
    @Value("${spring.datasource.druid.password}")
    public static String password;
    public Connection conn = null;
    public PreparedStatement pst = null;

   /* static {
        try {
            InputStream in = JDBCHelper.class.getClassLoader().getResourceAsStream("db.properties");
            Properties props = new Properties();
            props.load(in);
            name = props.getProperty("driverClassName");
            url = props.getProperty("url");
            user = props.getProperty("user");
            password = props.getProperty("password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    public JDBCHelper(String sql) {
        try {
            Class.forName(name);
            conn = DriverManager.getConnection(url, user, password);
            pst = conn.prepareStatement(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.pst.close();
            this.conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
