package de.stea1th.productservice.service;

import de.stea1th.productservice.converter.ProductConverter;
import de.stea1th.productservice.dto.ProductDto;
import de.stea1th.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductConverter productConverter;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductConverter productConverter) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
    }

    @Override
    public List<ProductDto> getAll(boolean withFrozen) {
        log.info(withFrozen ? "Getting all products" : "Getting all products without frozen");
        return productConverter.transformToDtoList(withFrozen ? productRepository.findAll() : productRepository.findAllWithoutFrozen(), true);
    }

    public ProductDto get(int id) {
        log.info("Getting product with id: {}", id);
        return productConverter.transformToDto(Objects.requireNonNull(productRepository.findById(id).orElse(null)), true);
    }



}
