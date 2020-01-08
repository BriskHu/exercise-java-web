package com.briskhu.exercize.springnetty.deviceclient.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <p/>
 *
 * @author Brisk Hu
 * created on 2020-01-08
 **/
public class PropertiesUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);

    /* ---------------------------------------- fileds ---------------------------------------- */
    private static Properties properties = null;


    /* ---------------------------------------- methods ---------------------------------------- */

    /**
     * 从properties文件中获取配置项
     *
     * @return
     */
    public static Properties getFileProperties() {
        InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties");
        try {
            if (properties == null) {
                properties = new Properties();
                properties.load(is);
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }


}


