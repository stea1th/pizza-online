package de.stea1th.orderservice.num;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public enum TimeInterval {

    TWO_WEEKS("Two weeks", LocalDateTime.now().minusWeeks(2)),
    SIX_MONTH("Six month", LocalDateTime.now().minusMonths(6));


    public final String description;
    public final LocalDateTime time;
}
