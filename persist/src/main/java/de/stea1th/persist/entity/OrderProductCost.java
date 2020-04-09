package de.stea1th.persist.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_cost_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    private ProductCost productCost;
}
