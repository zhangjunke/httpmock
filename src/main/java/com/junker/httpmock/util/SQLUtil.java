package com.junker.httpmock.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class SQLUtil {
    private static final String dbconfig = "/Properties/mysql.properties";
    private static Properties prop = new Properties();
    static {
        try {
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(dbconfig);
            prop.load(in);
            Class.forName(prop.getProperty("driverClassName"));
        } catch(IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(prop.getProperty("url"),
                    prop.getProperty("username"), prop.getProperty("password"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}