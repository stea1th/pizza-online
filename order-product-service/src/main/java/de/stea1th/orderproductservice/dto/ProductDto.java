package de.stea1th.orderproductservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@ToString(exclude = "picture")
public class ProductDto {

    private Integer id;

    private String name;

    private String description;

    private String picture;

    private boolean frozen;
}
