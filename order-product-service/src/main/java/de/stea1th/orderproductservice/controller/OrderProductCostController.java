package de.stea1th.orderproductservice.controller;

import com.netflix.ribbon.proxy.annotation.Http;
import de.stea1th.orderproductservice.dto.OrderProductCostDto;
import de.stea1th.orderproductservice.service.OrderProductCostService;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/api/")
public class OrderProductCostController {

    private final OrderProductCostService orderProductCostService;


    public OrderProductCostController(OrderProductCostService orderProductCostService) {
        this.orderProductCostService = orderProductCostService;
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> addToCart(@RequestBody OrderProductCostDto orderProductCostDto, Principal principal) {
        orderProductCostDto.setKeycloak(principal.getName());
        log.info("added to cart: {}", orderProductCostDto);
        Integer sum = orderProductCostService.addToCart(orderProductCostDto);
        return new ResponseEntity<>(sum, HttpStatus.OK);
    }

    @GetMapping(value = "/sum")
    public ResponseEntity<Integer> getQuantitiesSumInCart(Principal principal) {
//        String keycloak = principal.getName();
        String keycloak = "b04bf0fe-135e-4dc5-a130-48a0109543a6";
        log.info("retrieve sum of all products in cart for keycloak: {}", keycloak);
//        Integer sum = orderProductCostService.getQuantitiesSumInCart(keycloak);
        Integer sum = orderProductCostService.getQuantitiesSum(keycloak);
        return new ResponseEntity<>(sum, HttpStatus.OK);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> updateInCart(@RequestBody OrderProductCostDto orderProductCostDto, Principal principal) {
        orderProductCostDto.setKeycloak(principal.getName());
        log.info("update in cart: {}", orderProductCostDto);
//        Integer sum = orderProductCostService.updateInCart(orderProductCostDto);
        Integer sum = orderProductCostService.updateQuantityAndPriceWithDiscount(orderProductCostDto);
        return new ResponseEntity<>(sum, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{productCostId}")
    public ResponseEntity<Integer> deleteFromCart(@PathVariable("productCostId") int productCostId, Principal principal) {
        log.info("delete from cart {}", productCostId);
        var orderProductCostDto = new OrderProductCostDto();
//        orderProductCostDto.setKeycloak(principal.getName());

        String keycloak = "b04bf0fe-135e-4dc5-a130-48a0109543a6";
        orderProductCostDto.setKeycloak(keycloak);


        orderProductCostDto.setProductCostId(productCostId);
        Integer sum = orderProductCostService.deleteFromCart(orderProductCostDto);
        return new ResponseEntity<>(sum, HttpStatus.OK);
    }
}
