package de.stea1th.orderproductservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OrderProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderProductServiceApplication.class, args);
    }

}
