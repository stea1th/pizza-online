package de.stea1th.orderproductservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@Table(name = "order_product_cost", schema = "public", catalog = "pizza_online")
public class OrderProductCost {

    @EmbeddedId
    protected OrderProductCostPK id;

    @NotNull
    private Integer quantity;

    @NotBlank
    private BigDecimal price;

    @NotNull
    private Integer discount;

}
