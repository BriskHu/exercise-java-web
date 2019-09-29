package com.briskhu.springcloudconfiglocal;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudConfigLocalApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfigLocalApplication.class, args);
    }

}
