package de.stea1th.completedorderservice.converter;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ProductCostConverter {

//    private final ProductConverter productConverter;
//
//    public ProductCostConverter(@Lazy ProductConverter productConverter) {
//        this.productConverter = productConverter;
//    }
//
//    public ProductCostInCartDto convertToDtoInCart(ProductCost productCost, OrderProductCost orderProductCost) {
//        var productCostDto = new ProductCostInCartDto();
//        var productDto = productConverter.convertToDtoWithoutProductCostList(productCost.getProduct());
//        productCostDto.setProduct(productDto);
//        productCostDto.setId(productCost.getId());
//        productCostDto.setProperty(productCost.getProperty());
//        productCostDto.setDiscount(orderProductCost.getDiscount());
//        productCostDto.setPrice(orderProductCost.getPrice());
//        productCostDto.setQuantity(orderProductCost.getQuantity());
//        return productCostDto;
//    }
//
//    public ProductCostDto convertToDto(ProductCost productCost) {
//        var productCostDto = new ProductCostDto();
//        productCostDto.setId(productCost.getId());
//        productCostDto.setPrice(productCost.getPrice());
//        productCostDto.setDiscount(productCost.getDiscount());
//        productCostDto.setProperty(productCost.getProperty());
//        productCostDto.setFrozen(productCost.isFrozen());
//        return productCostDto;
//    }
//
//    public List<ProductCostDto> convertListToDto(List<ProductCost> list, boolean withFrozen) {
//        return withFrozen ?
//                list.stream()
//                        .map(this::convertToDto)
//                        .collect(Collectors.toList()) :
//                list.stream()
//                        .filter(el -> !el.isFrozen())
//                        .map(this::convertToDto)
//                        .collect(Collectors.toList());
//    }
}
