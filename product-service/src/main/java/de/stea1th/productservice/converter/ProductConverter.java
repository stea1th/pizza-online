package de.stea1th.productservice.converter;


import de.stea1th.productservice.dto.ProductDto;
import de.stea1th.productservice.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ProductConverter {

    private final ImageConverter imageConverter;

    public ProductConverter(ImageConverter imageConverter) {
        this.imageConverter = imageConverter;
    }

    public ProductDto transformToDto(Product product) {
        log.info("Transforming product: {} to transfer object", product);
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setFrozen(product.isFrozen());
        productDto.setPicture(imageConverter.convertImageToBase64(product.getPicture()));
        return productDto;
    }

    public List<ProductDto> transformToDtoList(List<Product> list) {
        return list.stream().map(this::transformToDto).collect(Collectors.toList());
    }
}
