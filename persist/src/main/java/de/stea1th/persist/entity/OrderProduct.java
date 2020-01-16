package de.stea1th.persist.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@Table(name = "order_product", schema = "public", catalog = "pizza_online")
public class OrderProduct {

    @EmbeddedId
    protected OrderProductPK id;

    @NotNull
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, updatable = false, insertable = false)
    private Product product;
}
