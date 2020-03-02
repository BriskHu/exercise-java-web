package com.briskhu.exercize.springredis.util;


import io.lettuce.core.RedisClient;
import org.junit.Test;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;

/**
 * <p/>
 *
 * @author Brisk Hu
 * created on 2020-01-18
 **/
public class RedisDistributedLockTest {

    @Test
    public void simpleLock() throws InterruptedException {
        RedisDistributedLock redisLock = new RedisDistributedLock();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName("127.0.0.1");
        standaloneConfiguration.setPort(6379);
        standaloneConfiguration.setPassword("");
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(standaloneConfiguration);
        connectionFactory.afterPropertiesSet();
        redisTemplate.setConnectionFactory(connectionFactory);

        redisLock.setRedisTemplate(redisTemplate);

        Thread.sleep(5000);
        Thread a = new Thread(() -> {
            redisLock.simpleLock("redisLock", System.currentTimeMillis() + 5000 + "");
        });
        a.start();

        Thread b = new Thread(() -> {
            redisLock.simpleLock("redisLock", System.currentTimeMillis() + 8000 + "");
        });
        b.start();
    }
}
