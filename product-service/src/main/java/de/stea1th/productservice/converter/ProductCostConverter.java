package de.stea1th.productservice.converter;

import de.stea1th.productservice.dto.ProductCostDto;
import de.stea1th.productservice.entity.ProductCost;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ProductCostConverter {

    private final ProductConverter productConverter;

    public ProductCostConverter(ProductConverter productConverter) {
        this.productConverter = productConverter;
    }

    public ProductCostDto transformToDto(ProductCost productCost) {
        log.info("Transforming product-cost: {} to transfer object", productCost);
        if(productCost == null) return null;
        ProductCostDto productCostDto = new ProductCostDto();
        productCostDto.setId(productCost.getId());
        productCostDto.setProperty(productCost.getProperty());
        productCostDto.setPrice(productCost.getPrice());
        productCostDto.setDiscount(productCost.getDiscount());
        productCostDto.setFrozen(productCost.isFrozen());
        productCostDto.setProduct(productConverter.transformToDto(productCost.getProduct()));
        return productCostDto;
    }

    public List<ProductCostDto> transformToDtoList(List<ProductCost> list) {
        log.info("Transforming product-cost list: {} to transfer object", list);
        return list.stream().map(this::transformToDto).collect(Collectors.toList());
    }

}
