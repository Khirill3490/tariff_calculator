package ru.fastdelivery.domain.delivery.distanceCalc;

import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public record DistanceCalculator(
        DistanceFactory distanceFactory,
        BigDecimal llat1,
        BigDecimal llong1,
        BigDecimal llat2,
        BigDecimal llong2) {
    private final static BigDecimal RADIUS = BigDecimal.valueOf(6_372);
    private static final MathContext mathContext = new MathContext(10);

    public DistanceCalculator{
        if(!distanceFactory.isValid(llat1, llat2, llong1, llong2)) {
            throw new IllegalArgumentException("The coordinates are not Valid!");
        }
    }


    public BigDecimal calcDist() {
        BigDecimal lat1 = llat1.multiply(BigDecimal.valueOf(Math.PI)).divide(BigDecimal.valueOf(180), 10, RoundingMode.HALF_UP);
        BigDecimal lat2 = llat2.multiply(BigDecimal.valueOf(Math.PI)).divide(BigDecimal.valueOf(180), 10, RoundingMode.HALF_UP);
        BigDecimal long1 = llong1.multiply(BigDecimal.valueOf(Math.PI)).divide(BigDecimal.valueOf(180), 10, RoundingMode.HALF_UP);
        BigDecimal long2 = llong2.multiply(BigDecimal.valueOf(Math.PI)).divide(BigDecimal.valueOf(180), 10, RoundingMode.HALF_UP);

        BigDecimal cl1 = BigDecimalMath.cos(lat1, mathContext);
        BigDecimal cl2 = BigDecimalMath.cos(lat2, mathContext);
        BigDecimal sl1 = BigDecimalMath.sin(lat1, mathContext);
        BigDecimal sl2 = BigDecimalMath.sin(lat2, mathContext);

        BigDecimal delta = long2.subtract(long1);
        BigDecimal cdelta = BigDecimalMath.cos(delta, mathContext);
        BigDecimal sdelta = BigDecimalMath.sin(delta, mathContext);

        BigDecimal y = BigDecimalMath.sqrt(cl2.multiply(sdelta).pow(2)
                .add(cl1.multiply(sl2).subtract(sl1.multiply(cl2).multiply(cdelta)).pow(2)),
                mathContext);
        BigDecimal x = sl1.multiply(sl2).add(cl1.multiply(cl2).multiply(cdelta));

        BigDecimal ad = BigDecimalMath.atan2(y, x, mathContext);

        return ad.multiply(RADIUS).setScale(4, RoundingMode.HALF_UP);
    }
}
