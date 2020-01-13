package com.briskhu.exercize.springnetty.loginserver;

import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * 登录服务器启动类
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.briskhu.exercize.springnetty.*")
public class SpringNettyLoginServerApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringNettyLoginServerApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.run(args);
        LoggerFactory.getLogger(SpringNettyLoginServerApplication.class).info("\n\n" +
                "****************** \t\t 设备登录服务器已启动成功 \t\t******************" +
                "\n\n");
    }

}
