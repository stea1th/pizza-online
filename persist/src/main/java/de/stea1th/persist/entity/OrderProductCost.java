package de.stea1th.persist.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@Table(name = "order_product_cost", schema = "public", catalog = "pizza_online")
public class OrderProductCost {

    @EmbeddedId
    protected OrderProductCostPK id;

    @NotNull
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_cost_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    private ProductCost productCost;
}
