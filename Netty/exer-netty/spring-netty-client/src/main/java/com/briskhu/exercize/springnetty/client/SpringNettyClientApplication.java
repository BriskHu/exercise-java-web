package com.briskhu.exercize.springnetty.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringNettyClientApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringNettyClientApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

}
