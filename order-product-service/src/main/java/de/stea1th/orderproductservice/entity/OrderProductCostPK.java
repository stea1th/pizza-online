package de.stea1th.orderproductservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@NoArgsConstructor
@Embeddable
public class OrderProductCostPK implements Serializable {


    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "order_id")
    private Integer orderId;

    @NotNull
    @Column(name = "product_cost_id")
    private Integer productCostId;

}
