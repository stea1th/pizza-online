package de.stea1th.orderwebsocketservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableEurekaClient
@EnableScheduling
public class OrderWebsocketServiceApplication {

    private final SimpMessagingTemplate messagingTemplate;

    public OrderWebsocketServiceApplication(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderWebsocketServiceApplication.class, args);
    }

    @Scheduled(fixedDelay = 3000L)
    public void time() {
        String message = "Hallo at: " +LocalDateTime.now().toString();
        messagingTemplate.convertAndSend("/topic/greetings", message);
    }

}
