package de.stea1th.web.controller;

import de.stea1th.commonslibrary.dto.OrderProductDto;
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
    public ResponseEntity addToCart(@RequestBody OrderProductDto orderProductDto, Principal principal) {
        orderProductDto.setKeycloak(principal.getName());
        log.info("added to cart: {}", orderProductDto);
        orderProductCostService.addToCart(orderProductDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
