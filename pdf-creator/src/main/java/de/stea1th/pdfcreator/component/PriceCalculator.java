package de.stea1th.pdfcreator.component;

import de.stea1th.commonslibrary.dto.ProductCostInCartDto;
import lombok.var;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class PriceCalculator {

    BigDecimal sumTotal(java.util.List<ProductCostInCartDto> dtoList) {
        double sum = dtoList
                .stream()
                .mapToDouble(x -> {
                    var price = x.getPrice().doubleValue();
                    return price * x.getQuantity();
                })
                .sum();
        return new BigDecimal(sum).setScale(2, RoundingMode.HALF_UP);
    }

    BigDecimal sumDiscount(java.util.List<ProductCostInCartDto> dtoList) {
        double sum = dtoList
                .stream()
                .filter(x -> x.getDiscount() > 0)
                .mapToDouble(dto -> calculateDiscount(dto) * dto.getQuantity())
                .sum();
        return new BigDecimal(sum).setScale(2, RoundingMode.HALF_UP);
    }

    double calculateDiscount(ProductCostInCartDto dto) {
        return dto.getPrice().doubleValue() * dto.getDiscount() / 100;
    }

    BigDecimal calculateTax(BigDecimal bigDecimal) {
        return bigDecimal.multiply(BigDecimal.valueOf(0.07)).setScale(2, RoundingMode.HALF_UP);
    }





}
