package com.briskhu.exercize.springnetty.device.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.briskhu.exercize.springnetty")
public class DeviceManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeviceManagerApplication.class, args);
    }

}
