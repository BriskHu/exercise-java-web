package com.briskhu.exercize.springnetty.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 在程序启动时检查数据库连接是否正常<p/>
 *
 * @author Brisk Hu
 * created on 2020-03-22
 **/
@Component
@ConditionalOnWebApplication
public class DataSourceConfig implements ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        LOGGER.info("[setApplicationContext] Validate the status of data source connection.");
        try {
            context = applicationContext;
            DataSource dataSource = (DataSource) context.getBean("dataSource");
            dataSource.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public ApplicationContext getApplicationContext() {
        return context;
    }
}
