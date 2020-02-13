package de.stea1th.commonlibrary.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {

    private LocalDateTime created;

    private Boolean completed;

    private List<OrderProductDto> orders;

    private PersonDto person;
}
