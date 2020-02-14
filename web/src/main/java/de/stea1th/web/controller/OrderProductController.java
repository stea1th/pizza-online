package de.stea1th.web.controller;

import de.stea1th.commonslibrary.dto.OrderProductDto;
import de.stea1th.web.service.OrderProductService;
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
public class OrderProductController {

    private final OrderProductService orderProductService;


    public OrderProductController(OrderProductService orderProductService) {
        this.orderProductService = orderProductService;
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody OrderProductDto orderProductDto, Principal principal) {
        orderProductDto.setKeycloak(principal.getName());
        log.info("!!!!!! {}", orderProductDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
