package de.stea1th.web.controller;

import de.stea1th.commonslibrary.dto.ProductCostInCartDto;
import de.stea1th.web.service.ProductCostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/api/product-cost")
public class ProductCostController {

    private ProductCostService productCostService;

    public ProductCostController(ProductCostService productCostService) {
        this.productCostService = productCostService;
    }


    @GetMapping("/cart")
    public ResponseEntity<List<ProductCostInCartDto>> getAllProductCostsInCart(Principal principal) {
        log.info("get all products in cart");
        List<ProductCostInCartDto> productDtoList = productCostService.getProductCostsInCart(principal.getName());
        return new ResponseEntity(HttpStatus.OK);
    }
}
