package de.stea1th.persist.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@Table(name = "product_cost", schema = "public", catalog = "pizza_online",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"product_id", "property"}, name = "product_unique_product_id_property_index")})
public class ProductCost extends AbstractBaseEntity {

    @NotBlank
    private String property;

    @NotBlank
    private BigDecimal price;

    @NotNull
    private Integer discount;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;
}
