package de.stea1th.productservice.controller;

import de.stea1th.productservice.dto.ProductDto;
import de.stea1th.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/products/{withFrozen}")
    public ResponseEntity<List<ProductDto>> getAll(@PathVariable("withFrozen") boolean withFrozen) {
        log.info("Getting all products {}", withFrozen ? "" : "without frozen");
        List<ProductDto> productDtoList = productService.getAll(withFrozen);
        log.info("Receiving list of products: {}", productDtoList);
        return new ResponseEntity<>(productDtoList, HttpStatus.ACCEPTED);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto> get(@PathVariable("id") int id) {
        log.info("Getting product with id: {}", id);
        ProductDto product = productService.get(id);
        return product != null ? new ResponseEntity<>(product, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
