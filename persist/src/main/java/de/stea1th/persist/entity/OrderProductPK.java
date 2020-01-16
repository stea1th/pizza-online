package de.stea1th.persist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrderProductPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Integer orderId;

    @NotNull
    private Integer productId;
}
