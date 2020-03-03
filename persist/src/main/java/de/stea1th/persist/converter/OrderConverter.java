package de.stea1th.persist.converter;

import de.stea1th.commonslibrary.dto.OrderDto;
import de.stea1th.persist.entity.Order;
import lombok.var;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter {

    private LocalDateTimeConverter localDateTimeConverter;

    private PersonConverter personConverter;


    public OrderConverter(LocalDateTimeConverter localDateTimeConverter, PersonConverter personConverter) {
        this.localDateTimeConverter = localDateTimeConverter;
        this.personConverter = personConverter;
    }


    public OrderDto convertToDtoWithoutOPC(Order order) {
        var dto = new OrderDto();
        dto.setId(order.getId());
        dto.setCreated(localDateTimeConverter.convertToDto(order.getCreated()));
        dto.setCompleted(localDateTimeConverter.convertToDto(order.getCompleted()));
        dto.setPerson(personConverter.convertToDto(order.getPerson()));
        return dto;
    }
}
