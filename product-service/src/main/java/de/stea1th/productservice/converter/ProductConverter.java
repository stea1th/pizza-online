package de.stea1th.productservice.converter;


import de.stea1th.productservice.dto.ProductDto;
import de.stea1th.productservice.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ProductConverter {

    private final ImageConverter imageConverter;

    private final ProductCostConverter productCostConverter;

    public ProductConverter(ImageConverter imageConverter, @Lazy ProductCostConverter productCostConverter) {
        this.imageConverter = imageConverter;
        this.productCostConverter = productCostConverter;
    }

    public ProductDto transformToDto(Product product, boolean withProductCosts) {
        log.info("Transforming product: {} {} product-costs to transfer object", product, withProductCosts ? "with" : "without");
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setFrozen(product.isFrozen());
        productDto.setPicture(imageConverter.convertImageToBase64(product.getPicture()));
        if (withProductCosts) {
            log.info("Adding product-cost list to transfer object");
            productDto.setProductCostList(productCostConverter.transformToDtoList(product.getProductCostList(), false));
        }
        return productDto;
    }

    public List<ProductDto> transformToDtoList(List<Product> list, boolean withProductCosts) {
        log.info("Transforming product-cost list: {} {} product-costs to transfer object", list, withProductCosts ? "with" : "without");
        return list.stream().map(el -> transformToDto(el, withProductCosts)).collect(Collectors.toList());
    }
}
