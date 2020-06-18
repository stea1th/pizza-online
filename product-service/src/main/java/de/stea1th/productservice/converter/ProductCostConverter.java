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

    public ProductCostDto transformToDto(ProductCost productCost, boolean withProduct) {
        log.info("Transforming product-cost: {} {} product to transfer object", productCost, withProduct? "with" : "without");
        if(productCost == null) return null;
        ProductCostDto productCostDto = new ProductCostDto();
        productCostDto.setId(productCost.getId());
        productCostDto.setProperty(productCost.getProperty());
        productCostDto.setPrice(productCost.getPrice());
        productCostDto.setDiscount(productCost.getDiscount());
        productCostDto.setFrozen(productCost.isFrozen());
        if(withProduct) {
            log.info("Adding product to transfer object");
            productCostDto.setProduct(productConverter.transformToDto(productCost.getProduct(), false));
        }

        return productCostDto;
    }

    public List<ProductCostDto> transformToDtoList(List<ProductCost> list, boolean withProduct) {
        log.info("Transforming product-cost list: {} {} product to transfer object", list, withProduct? "with" : "without");
        return list.stream().map(el-> transformToDto(el, withProduct)).collect(Collectors.toList());
    }

}
