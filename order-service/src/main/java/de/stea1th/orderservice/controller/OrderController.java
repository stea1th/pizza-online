package de.stea1th.orderservice.controller;


import de.stea1th.orderservice.entity.Order;
import de.stea1th.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/api/")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/complete")
    public ResponseEntity<LocalDateTime> completeOrder(Principal principal) {
        String keycloak = principal.getName();
        log.info("Completing order for keycloak: {}", keycloak);
        Order order = orderService.complete(keycloak);
        LocalDateTime complete = order.getCompleted();
        return complete == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(complete, HttpStatus.OK);
    }

    @GetMapping("/interval")
    public ResponseEntity<Map<String, String>> getInterval(Principal principal) {
        String keycloak = principal.getName();
        log.info("Getting time interval for keycloak: {}", keycloak);
        return new ResponseEntity<>(orderService.getInterval(keycloak), HttpStatus.OK);
    }
}
