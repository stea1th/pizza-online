package de.stea1th.completedorderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto extends AbstractBaseDto {

    private LocalDateTime created;

    private LocalDateTime completed;

    private List<OrderProductCostDto> orders;

    private PersonDto person;
}
