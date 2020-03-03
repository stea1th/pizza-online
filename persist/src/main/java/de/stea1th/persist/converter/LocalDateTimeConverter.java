package de.stea1th.persist.converter;

import de.stea1th.commonslibrary.dto.LocalDateTimeDto;
import lombok.val;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LocalDateTimeConverter {

    public LocalDateTimeDto convertToDto(LocalDateTime localDateTime) {
        val dto = new LocalDateTimeDto();
        dto.setYear(localDateTime.getYear());
        dto.setMonthValue(localDateTime.getMonthValue());
        dto.setDayOfMonth(localDateTime.getDayOfMonth());
        dto.setHour(localDateTime.getHour());
        dto.setMinute(localDateTime.getMinute());
        dto.setSecond(localDateTime.getSecond());
        return dto;
    }

}
