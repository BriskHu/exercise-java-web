package com.briskhu.exercize.springnetty.common.config;

import io.netty.util.concurrent.ThreadPerTaskExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p/>
 *
 * @author Brisk Hu
 * created on 2019-12-26
 **/
@Configuration
public class TreadPoolConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(TreadPoolConfig.class);

    /* ---------------------------------------- fileds ---------------------------------------- */


    /* ---------------------------------------- methods ---------------------------------------- */
    @Bean("nettyExecutor")
    public Executor nettyExecutor(){
        ThreadPoolTaskExecutor nettyExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数
        nettyExecutor.setCorePoolSize(10);
        nettyExecutor.setMaxPoolSize(30);
        nettyExecutor.setQueueCapacity(20);
        nettyExecutor.setThreadNamePrefix("netty-thread-");
        // 当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        nettyExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        nettyExecutor.initialize();
        return nettyExecutor;
    }


}


