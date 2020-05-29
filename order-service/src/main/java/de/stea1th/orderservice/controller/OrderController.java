package de.stea1th.orderservice.controller;


import de.stea1th.orderservice.entity.Order;
import de.stea1th.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/api/")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

//    @GetMapping("/complete")
//    public ResponseEntity<LocalDateTimeDto> completeOrder(Principal principal) {
//        String keycloak = principal.getName();
//        LocalDateTimeDto complete = orderService.complete(keycloak);
//        return complete == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
//                new ResponseEntity<>(complete, HttpStatus.OK);
//    }
//
//    @GetMapping("/interval")
//    public ResponseEntity<List<TimeIntervalDto>> getInterval(Principal principal) {
//        String keycloak = principal.getName();
//        return new ResponseEntity<>(orderService.getInterval(keycloak), HttpStatus.OK);
//    }

//    @GetMapping("/all/completed")
//    public ResponseEntity<List<CompletedOrderDto>> getCompletedOrders(Principal principal, @RequestParam("value") String value) {
//        CompletedOrdersRequestDto completedOrdersRequestDto = new CompletedOrdersRequestDto(principal.getName(), value);
//        return new ResponseEntity<>(orderService.getCompletedOrders(completedOrdersRequestDto), HttpStatus.OK);
//    }

    @GetMapping("/{keycloak}")
    public ResponseEntity<Order> getByKeycloak(@PathVariable("keycloak") String keycloak) {
        return new ResponseEntity<>(orderService.getUncompletedOrderByPersonKeycloak(keycloak), HttpStatus.OK);
    }

    @GetMapping("/complete/{keycloak}")
    public ResponseEntity<Order> completeByKeycloak(@PathVariable("keycloak") String keycloak) {
        return new ResponseEntity<>(orderService.complete(keycloak), HttpStatus.OK);
    }
}
