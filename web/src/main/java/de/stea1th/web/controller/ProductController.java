package de.stea1th.web.controller;

import de.stea1th.kafkalibrary.dto.ProductDto;
import de.stea1th.web.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/product")
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
}
