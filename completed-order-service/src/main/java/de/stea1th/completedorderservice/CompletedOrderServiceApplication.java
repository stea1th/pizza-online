package de.stea1th.completedorderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CompletedOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompletedOrderServiceApplication.class, args);
    }

}
