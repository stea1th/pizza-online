package de.stea1th.orderwebsocketservice.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {

    @MessageMapping("/test")
    @SendTo("/topic/test")
    public String test(@Payload String message) {
        log.info("Incoming message is: {}", message);
        return "Hello " + message + " !";
    }
}
