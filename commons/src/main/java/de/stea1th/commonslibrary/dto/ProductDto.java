package de.stea1th.commonslibrary.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(exclude = "picture")
public class ProductDto extends AbstractBaseDto {

    private String name;

    private String description;

    private String picture;

    private List<ProductCostDto> productCostList;
}
