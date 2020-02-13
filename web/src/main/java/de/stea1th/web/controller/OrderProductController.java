package de.stea1th.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.commonlibrary.dto.OrderProductDto;
import de.stea1th.web.service.OrderProductService;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity save(@RequestBody String string) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        var orderProductDto = objectMapper.readValue(string, OrderProductDto.class);
        log.info("!!!!!! {}", orderProductDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
