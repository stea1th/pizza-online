package de.stea1th.web.kafka;

import de.stea1th.commonslibrary.dto.OrderDto;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderKafkaConsumer {

    OrderDto getOrderDto();

    List<Integer> getYears();
}
