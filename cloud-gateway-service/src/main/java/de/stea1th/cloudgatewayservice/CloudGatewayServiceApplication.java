package de.stea1th.cloudgatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CloudGatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudGatewayServiceApplication.class, args);
    }

}
