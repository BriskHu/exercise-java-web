package com.briskhu.exercize.springredis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * <p/>
 *
 * @author Brisk Hu
 * created on 2020-01-18
 **/
public class RedisConnector {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConnector.class);

    /* ---------------------------------------- fileds ---------------------------------------- */
    private Boolean isCluster;

    private List<String> nodes;

    private String host;

    private int port;

    private String password;


    /* ---------------------------------------- methods ---------------------------------------- */

    /**
     * 获取Redis的配置参数
     *
     * @param environment
     */
    @PostConstruct
    public void initConnectInfo(@Autowired Environment environment){
        isCluster = Boolean.parseBoolean(environment.getProperty("spring.redis.isCluster"));
        if (isCluster == null || isCluster == false) {
            host = environment.getProperty("spring.redis.host");
            port = Integer.parseInt(environment.getProperty("spring.redis.port"));
        }
        else if (isCluster != null && isCluster == true){
            try{
                nodes = Arrays.asList(environment.getProperty("spring.redis.nodes").split(","));
            }
            catch (Exception e){
                LOGGER.error("[initConnectInfo] redis配置参数有误。");
                e.printStackTrace();
                throw e;
            }
        }
        password = environment.getProperty("spring.redis.password");
    }

    /**
     * 获取Lettuce连接工厂-单机模式
     *
     * @param host
     * @param port
     * @param password
     * @param database
     * @return
     */
    public static LettuceConnectionFactory getLettuceConnector(String host, int port, String password, int database) {
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName(host);
        standaloneConfiguration.setPort(port);
        standaloneConfiguration.setPassword(password);
        standaloneConfiguration.setDatabase(database);

        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(standaloneConfiguration);
        connectionFactory.afterPropertiesSet();       // TODO-note 这句很关键，如果忘记写，会导致Redis连接不上，从而报空指针。
        return connectionFactory;
    }

    /**
     * 获取Lettuce连接工厂-集群模式
     *
     * @param nodes ip:port形式的字符串列表，例如127.0.0.1:6379。
     * @param password
     * @return
     */
    public static LettuceConnectionFactory getLettuceConnector(List<String> nodes, String password) {
        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(nodes);
        clusterConfiguration.setPassword(password);
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(clusterConfiguration);
        connectionFactory.afterPropertiesSet();
        return connectionFactory;
    }

    /**
     * 获取Lettuce连接工厂-哨兵模式
     *
     * @param masterName 主节点名
     * @param sentinels ip:port形式的字符串列表，例如127.0.0.1:6379。
     * @param password
     * @param database
     * @return
     */
    public static LettuceConnectionFactory getLettuceConnector(String masterName, Set<String> sentinels, String password, int database) {
        RedisSentinelConfiguration sentinelConfiguration = new RedisSentinelConfiguration(masterName, sentinels);
        sentinelConfiguration.setPassword(password);
        sentinelConfiguration.setDatabase(database);
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(sentinelConfiguration);
        connectionFactory.afterPropertiesSet();
        return connectionFactory;
    }




    public List<String> getNodes() {
        return nodes;
    }
}


