package de.stea1th.productservice.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@Data
@NoArgsConstructor
@ToString(exclude = "picture")
public class ProductDto {

    private Integer id;

    private String name;

    private String description;

    private String picture;

    private boolean frozen;

    private List<ProductCostDto> productCostList;
}
