package de.stea1th.productservice.controller;

import de.stea1th.productservice.dto.ProductDto;
import de.stea1th.productservice.entity.Product;
import de.stea1th.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/api/")
//@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductDto>> getAll() {
        log.info("Getting all products");
        List<ProductDto> productDtoList = productService.getAll(true);
        log.info("Receiving list of products: {}", productDtoList);
        return new ResponseEntity<>(productDtoList, HttpStatus.ACCEPTED);
    }

    @GetMapping("/product/no-frozen")
    public ResponseEntity<List<ProductDto>> getAllWithoutFrozen() {
        log.info("Getting all products without frozen");
        List<ProductDto> products = productService.getAll(false);
        log.info("Receiving list of products without frozen: {}", products);
        return new ResponseEntity<>(products, HttpStatus.ACCEPTED);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto> get(@PathVariable("id") int id) {
        log.info("Getting product with id: {}", id);
        ProductDto product = productService.get(id);
        return product != null? new ResponseEntity<>(product, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
