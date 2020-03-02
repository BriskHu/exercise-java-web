package com.briskhu.exercize.springredis.util;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * 使用Lettuce原生Api连接Redis<p/>
 *
 * @author Brisk Hu
 * created on 2020-01-19
 **/
public class LettuceConnectTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(LettuceConnectTest.class);

    /* ---------------------------------------- fileds ---------------------------------------- */


    /* ---------------------------------------- object methods ---------------------------------------- */
    public void lettuceConnector(){
        RedisClient redisClient = RedisClient.create("redis://123456@127.0.0.1:6379/8");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> commands = connection.sync();
        String future = commands.get("redisKey");
        System.out.println(future);
        System.out.println(connection.sync().dbsize());
    }


    /* ---------------------------------------- test methods ---------------------------------------- */
    @Test
    public void lettuceConnectorTest() {
        LettuceConnectTest connectTest = new LettuceConnectTest();
        connectTest.lettuceConnector();
    }


}


