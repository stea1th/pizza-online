package de.stea1th.completedorderservice.controller;

import de.stea1th.completedorderservice.dto.OrderDto;
import de.stea1th.completedorderservice.service.CompletedOrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/")
@Slf4j
public class CompletedOrderController {

    private final CompletedOrderServiceImpl completedOrderService;

    public CompletedOrderController(CompletedOrderServiceImpl completedOrderService) {
        this.completedOrderService = completedOrderService;
    }

    @GetMapping(value = "/completed")
    public ResponseEntity<OrderDto> getCompletedOrders() {

//        return new ResponseEntity<>(completedOrderService.getCompletedOrders(principal.getName(), value), HttpStatus.OK);
        String keycloak = "b04bf0fe-135e-4dc5-a130-48a0109543a6";
        return new ResponseEntity<>(completedOrderService.test(keycloak), HttpStatus.OK);
    }


}
