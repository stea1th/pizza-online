package de.stea1th.commonslibrary.num;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TimeInterval {

    SIX_MONTH("Six month"),
    ONE_YEAR("One year");

    public final String description;
}
