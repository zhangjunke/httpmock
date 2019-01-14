package com.junker.httpmock.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class SQLUtil {
    private static Properties prop = new Properties();
    static {
        String os = System.getProperty("os.name");
        String classPath = System.getProperty("user.dir");
        String dbConfPath="";
        if(os.toLowerCase().startsWith("win")){
            dbConfPath = classPath+"\\src\\main\\webapp\\Properties\\mysql.properties";
        }else{
            dbConfPath = classPath+"/src/main/webapp/Properties/mysql.properties";
        }
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(dbConfPath));
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