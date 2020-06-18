package de.stea1th.productservice.controller;

import de.stea1th.productservice.dto.ProductCostDto;
import de.stea1th.productservice.entity.ProductCost;
import de.stea1th.productservice.service.ProductCostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/api/")
public class ProductCostController {

    private final ProductCostService productCostService;

    public ProductCostController(ProductCostService productCostService) {
        this.productCostService = productCostService;
    }

    @GetMapping("/product-cost")
    public ResponseEntity<List<ProductCostDto>> getAll() {
        log.info("Getting all product-costs");
        return new ResponseEntity<>(productCostService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/product-cost/{id}")
    public ResponseEntity<ProductCostDto> get(@PathVariable("id") int id) {
        log.info("Getting product-cost with id: {}", id);
        ProductCostDto productCost = productCostService.get(id);
        return productCost != null ? new ResponseEntity<>(productCost, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
