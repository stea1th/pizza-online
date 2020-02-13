package de.stea1th.commonlibrary.dto;


import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDto implements Serializable {

    private int orderId;

    private int productId;

    private int quantity;
}
