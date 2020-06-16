package de.stea1th.orderproductservice.converter;

import de.stea1th.orderproductservice.dto.CartElementDto;
import de.stea1th.orderproductservice.dto.ProductCostDto;
import de.stea1th.orderproductservice.entity.OrderProductCost;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CartElementConverter {

    public CartElementDto convertToDto(OrderProductCost orderProductCost, ProductCostDto productCostDto) {
        log.info("Converting into new cart element");
        CartElementDto cartElementDto = new CartElementDto();
        cartElementDto.setId(productCostDto.getId());
        cartElementDto.setProperty(productCostDto.getProperty());
        cartElementDto.setProduct(productCostDto.getProduct());
        cartElementDto.setPrice(orderProductCost.getPrice());
        cartElementDto.setDiscount(orderProductCost.getDiscount());
        cartElementDto.setQuantity(orderProductCost.getQuantity());
        return cartElementDto;
    }

}
