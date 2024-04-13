package ru.fastdelivery.domain.delivery.distanceCalc;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Range {
    private BigDecimal min;
    private BigDecimal max;
}
