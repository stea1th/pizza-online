package de.stea1th.web.controller;

import de.stea1th.commonslibrary.dto.ProductDto;
import de.stea1th.web.service.ProductService;
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
@RequestMapping("/api/product")
//@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll() {
        log.info("get all products");
        List<ProductDto> productDtoList = productService.getAll();
        log.info("received list of products: {}", productDtoList);
        return new ResponseEntity<>(productDtoList, HttpStatus.ACCEPTED);
    }

    @GetMapping("/cart")
    public ResponseEntity<List<ProductDto>> getAllProductsInCart(Principal principal) {
        log.info("get all products in cart");
        List<ProductDto> productDtoList = productService.getProductsInCart(principal.getName());
        log.info("received list of products in cart: {}", productDtoList);
        return new ResponseEntity<>(productDtoList, HttpStatus.ACCEPTED);
    }
}
