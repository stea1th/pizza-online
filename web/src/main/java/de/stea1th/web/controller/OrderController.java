package de.stea1th.web.controller;


import de.stea1th.commonslibrary.dto.LocalDateTimeDto;
import de.stea1th.web.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/api/order")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/complete")
    public ResponseEntity<LocalDateTimeDto> completeOrder(Principal principal) {
        String keycloak = principal.getName();
        LocalDateTimeDto complete = orderService.complete(keycloak);
        return complete == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
//                new ResponseEntity<>("'" + complete.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) + "'", HttpStatus.OK);
                new ResponseEntity<>(complete, HttpStatus.OK);
    }
}
