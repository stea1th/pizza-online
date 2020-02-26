package de.stea1th.persist.converter;

import de.stea1th.commonslibrary.dto.ProductCostDto;
import de.stea1th.commonslibrary.dto.ProductCostInCartDto;
import de.stea1th.persist.entity.ProductCost;
import lombok.var;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ProductCostConverter {

    private final ProductConverter productConverter;

    public ProductCostConverter(@Lazy ProductConverter productConverter) {
        this.productConverter = productConverter;
    }

    public ProductCostInCartDto convertToDtoInCart(ProductCost productCost, int quantity) {
        var productCostDto = new ProductCostInCartDto();
        var productDto = productConverter.convertToDtoWithoutProductCostList(productCost.getProduct());
        productCostDto.setProduct(productDto);
        productCostDto.setId(productCost.getId());
        productCostDto.setProperty(productCost.getProperty());
        productCostDto.setDiscount(productCost.getDiscount());
        productCostDto.setPrice(productCost.getPrice());
        productCostDto.setQuantity(quantity);
        return productCostDto;
    }

    public ProductCostDto convertToDto(ProductCost productCost) {
        var productCostDto = new ProductCostDto();
        productCostDto.setId(productCost.getId());
        productCostDto.setPrice(productCost.getPrice());
        productCostDto.setDiscount(productCost.getDiscount());
        productCostDto.setProperty(productCost.getProperty());
        return productCostDto;
    }
}
