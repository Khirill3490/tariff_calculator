package ru.fastdelivery.domain.common.dimension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public record Dimension(Length length,
                        Length width,
                        Length height) {

    private static final BigDecimal BILLION = BigDecimal.valueOf(1_000_000_000);

    public Dimension {
    }

    public BigDecimal getSizeByParameters(Dimension dim) {
        BigInteger result = BigInteger.ONE;

        result = result.multiply(dim.length.param());
        result = result.multiply(dim.width.param());
        result = result.multiply(dim.height.param());

        BigDecimal resultBigDecimal = new BigDecimal(result);
        return resultBigDecimal.divide(BILLION, 4, RoundingMode.HALF_UP);
    }

    public BigDecimal add(BigDecimal bigDecimal) {
        return getSizeByParameters(this).add(bigDecimal);
    }

    public static BigDecimal zero() {
        return BigDecimal.ZERO;
    }

    public Dimension roundDim(Dimension dim) {
        return new Dimension(
                dim.length.getRoundLength(length),
                dim.width.getRoundLength(width),
                dim.height.getRoundLength(height));
    }




}
