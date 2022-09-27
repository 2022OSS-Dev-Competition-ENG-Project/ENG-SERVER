package com.example.engdiscoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EngDiscoveryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EngDiscoveryServiceApplication.class, args);
    }

}
