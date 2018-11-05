package com.junker.httpmock.util;

import com.junker.httpmock.dao.SearchMockConfDAO;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static Logger logger =Logger.getLogger(SearchMockConfDAO.class.getName());
    private static final String dbconfig = "/Properties/mysql.properties";
    private static Properties prop = new Properties();
    public static String getPropertiesValue(String key) {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(dbconfig);
        try {
            prop.load(in);
        } catch (IOException e) {
            logger.debug(e.toString());
        }
        return prop.getProperty(key);
    }
}
