package de.stea1th.persist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@NoArgsConstructor
//@AllArgsConstructor
@Embeddable
public class OrderProductPK implements Serializable {


    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "order_id")
    private Integer orderId;

    @NotNull
    @Column(name = "product_id")
    private Integer productId;

}
