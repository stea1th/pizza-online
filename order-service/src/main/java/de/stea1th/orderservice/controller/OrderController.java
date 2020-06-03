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
//        String keycloak = principal.getName();

        String keycloak = "b04bf0fe-135e-4dc5-a130-48a0109543a6";

        Order order = orderService.complete(keycloak);
        LocalDateTime complete = order.getCompleted();
        return complete == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(complete, HttpStatus.OK);
    }

    @GetMapping("/interval")
    public ResponseEntity<Map<String, String>> getInterval(Principal principal) {
//        String keycloak = principal.getName();
        String keycloak = "b04bf0fe-135e-4dc5-a130-48a0109543a6";
        return new ResponseEntity<>(orderService.getInterval(keycloak), HttpStatus.OK);
    }


    @GetMapping("/{keycloak}")
    public ResponseEntity<Order> getByKeycloak(@PathVariable("keycloak") String keycloak) {
        return new ResponseEntity<>(orderService.getUncompletedOrderByPersonKeycloak(keycloak), HttpStatus.OK);
    }

    @GetMapping("/complete/{keycloak}")
    public ResponseEntity<Order> completeByKeycloak(@PathVariable("keycloak") String keycloak) {
        return new ResponseEntity<>(orderService.complete(keycloak), HttpStatus.OK);
    }
}
