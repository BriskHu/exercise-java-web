package com.briskhu.exercize.springredis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

/**
 * Redis分布式锁<p/>
 *
 * @author Brisk Hu
 * created on 2020-01-17
 **/
public class RedisDistributedLock {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisDistributedLock.class);

    /* ---------------------------------------- fileds ---------------------------------------- */
    private RedisTemplate redisTemplate;


    /* ---------------------------------------- methods ---------------------------------------- */
    public void setRedisTemplate(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }


    /**
     * 简单的Redis分布式锁加锁
     *
     * @param key
     * @param expireTime
     * @return
     */
    public boolean simpleLock(String key, String expireTime) {
        System.out.println("dbsize = " + redisTemplate.getConnectionFactory().getConnection().dbSize());
        if (redisTemplate.opsForValue().setIfAbsent(key, expireTime)) {
            LOGGER.debug("[simpleLock] thread = {}, key = {}, expireTime = {}.", Thread.currentThread().getName(), key, expireTime);
            return true;
        }

        // 当前值。多线程情形下，每个线程在此处拿到的都是相同的值。
        String oldValue = (String) redisTemplate.opsForValue().get(key);
        // 如果锁未过期
        if (!StringUtils.isEmpty(oldValue) && (Long.parseLong(oldValue) < System.currentTimeMillis())) {
            // 获取本次设置之前的值。在多线程情形下，如果key设置前的值和上一步拿到的值相等，则表明数据是一致的。
            String beforeSetValue = (String) redisTemplate.opsForValue().getAndSet(key, expireTime);
            LOGGER.debug("[simpleLock] thread = {}, oldValue = {}, beforeSetValue = {}.", Thread.currentThread().getName(), oldValue, beforeSetValue);
            if (!StringUtils.isEmpty(beforeSetValue) && beforeSetValue.equals(oldValue)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 简单的Redis分布式锁解锁
     *
     * @param key
     * @param expireTime
     */
    public void simpleUnlock(String key, String expireTime) {
        try {
            String oldValue = (String) redisTemplate.opsForValue().get(key);

            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(expireTime)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            LOGGER.error("[simpleUnlock] Redis unlock failed.");
            e.printStackTrace();
        }
    }

    /**
     * 加分布式锁
     *
     * @return
     */
    public String lock() {
//        redisTemplate.execute()

        return null;
    }


    public static void main(String[] args) throws InterruptedException {
        RedisDistributedLock redisLock = new RedisDistributedLock();
        redisLock.setRedisTemplate(RedisTemplateUtil.getStringRedisTemplate());

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


