package ru.fastdelivery.domain.delivery.distanceCalc;


import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;


@RequiredArgsConstructor
public class DistanceFactory {
    private final DistanceProvider distanceProvider;

    public boolean isValid(BigDecimal a, BigDecimal b, BigDecimal c, BigDecimal d) {
        boolean latValid = isPointsValid(distanceProvider.getLatitude(), a, b);

        boolean longValid = isPointsValid(distanceProvider.getLongitude(), c, d);

        return latValid && longValid;
    }

    private boolean isPointsValid(Range range, BigDecimal a, BigDecimal b) {
        return (a.compareTo(range.getMin()) >= 0 && a.compareTo(range.getMax()) <= 0)
                && (b.compareTo(range.getMin()) >= 0 && b.compareTo(range.getMax()) <= 0);
    }
}
