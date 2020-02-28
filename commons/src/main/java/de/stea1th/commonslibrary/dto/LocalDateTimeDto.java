package de.stea1th.commonslibrary.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocalDateTimeDto {
    private int year;
    private int monthValue;
    private int dayOfMonth;
    private int hour;
    private int minute;
    private int second;
}
