package de.stea1th.commonslibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto extends AbstractBaseDto {

    private LocalDateTime created;

    private Boolean completed;

    private List<OrderProductCostDto> orders;

    private PersonDto person;
}
