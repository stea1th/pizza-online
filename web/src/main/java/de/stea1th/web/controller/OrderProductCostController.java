package de.stea1th.web.controller;

import de.stea1th.commonslibrary.dto.OrderProductCostDto;
import de.stea1th.web.service.OrderProductCostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/api/order_product")
public class OrderProductCostController {

    private final OrderProductCostService orderProductCostService;


    public OrderProductCostController(OrderProductCostService orderProductCostService) {
        this.orderProductCostService = orderProductCostService;
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addToCart(@RequestBody OrderProductCostDto orderProductCostDto, Principal principal) {
        orderProductCostDto.setKeycloak(principal.getName());
        log.info("added to cart: {}", orderProductCostDto);
        orderProductCostService.addToCart(orderProductCostDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
