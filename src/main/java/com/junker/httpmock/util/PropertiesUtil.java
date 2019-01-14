package com.junker.httpmock.util;

import com.junker.httpmock.dao.SearchMockConfDAO;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static Logger logger =Logger.getLogger(SearchMockConfDAO.class.getName());
    private static Properties prop = new Properties();
    public static String getPropertiesValue(String confPath,String key) {
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(confPath));
            prop.load(in);
        } catch (IOException e) {
            logger.debug(e.toString());
        }
        return prop.getProperty(key);
    }
}
