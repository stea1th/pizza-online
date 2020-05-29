package de.stea1th.productservice.controller;

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
    public ResponseEntity<List<Product>> getAll() {
        log.info("get all products");
        List<Product> productDtoList = productService.getAll(true);
        log.info("received list of products: {}", productDtoList);
        return new ResponseEntity<>(productDtoList, HttpStatus.ACCEPTED);
    }

    @GetMapping("/product/no-frozen")
    public ResponseEntity<List<Product>> getAllWithoutFrozen() {
        log.info("get all products without frozen");
        List<Product> productDtoList = productService.getAll(false);
        log.info("received list of products: {}", productDtoList);
        return new ResponseEntity<>(productDtoList, HttpStatus.ACCEPTED);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> get(@PathVariable("id") int id) {
        log.info("get product with id: {}", id);
        Product product = productService.get(id);
        return product != null? new ResponseEntity<>(product, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

//    @GetMapping("/cart")
//    public ResponseEntity<List<Product>> getAllProductsInCart(Principal principal) {
//        log.info("get all products in cart");
//        List<ProductDto> productDtoList = productService.getProductsInCart(principal.getName());
//        log.info("received list of products in cart: {}", productDtoList);
//        return new ResponseEntity<>(productDtoList, HttpStatus.ACCEPTED);
//    }
}
