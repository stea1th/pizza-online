package de.stea1th.web.kafka;

import de.stea1th.commonslibrary.dto.OrderDto;

import java.time.LocalDateTime;

public interface OrderKafkaConsumer {

    LocalDateTime getLocalDateTime();

    OrderDto getOrderDto();
}
