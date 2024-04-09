package ru.fastdelivery.domain.delivery.distanceCalc;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@Component
public class Range {
    private BigDecimal min;
    private BigDecimal max;
}
