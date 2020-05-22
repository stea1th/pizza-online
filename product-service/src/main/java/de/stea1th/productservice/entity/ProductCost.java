package de.stea1th.productservice.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
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

    private boolean frozen;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;
}
