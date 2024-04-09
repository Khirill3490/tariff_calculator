package ru.fastdelivery.domain.common.dimension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public record Dimension(BigInteger length,
                        BigInteger width,
                        BigInteger height) {

    private static final BigDecimal BILLION = BigDecimal.valueOf(1_000_000_000);
    private static final BigInteger MAX_VALUE = BigInteger.valueOf(1_500);
    private static final BigInteger ROUND = new BigInteger("50");

    public Dimension {
        if (isAnyParamNotValid(length, width, height)) {
            throw new IllegalArgumentException("The parameters cannot be below or equals Zero!");
        }
    }

    private static boolean isAnyParamNotValid(BigInteger length, BigInteger width, BigInteger height) {
        return (length.compareTo(BigInteger.ZERO) < 0 || length.compareTo(MAX_VALUE) > 0) ||
                (width.compareTo(BigInteger.ZERO) < 0 || width.compareTo(MAX_VALUE) > 0) ||
                (height.compareTo(BigInteger.ZERO) < 0 || height.compareTo(MAX_VALUE) > 0);
    }

    public BigDecimal getSizeByParameters(Dimension dim) {
        BigInteger result = BigInteger.ONE;

        result = result.multiply(dim.length);
        result = result.multiply(dim.width);
        result = result.multiply(dim.height);

        BigDecimal resultBigDecimal = new BigDecimal(result);
        return resultBigDecimal.divide(BILLION, 4, RoundingMode.HALF_UP);
    }

//    public BigDecimal getMeter(BigInteger bigInteger) {
//        BigDecimal resultBigDecimal = new BigDecimal(bigInteger);
//        return resultBigDecimal.divide(million, 4, RoundingMode.HALF_UP);
//    }

//    public BigDecimal add(Dimension additionalDim) {
//        return getSizeByParameters(this).add(getSizeByParameters(additionalDim));
//    }

    public BigDecimal add(BigDecimal bigDecimal) {
        return getSizeByParameters(this).add(bigDecimal);
    }

    public static BigDecimal zero() {
        return BigDecimal.ZERO;
    }

    public Dimension roundDim(Dimension dim) {
        return new Dimension(
                roundUp(dim.length),
                roundUp(dim.width),
                roundUp(dim.height));
    }

    private BigInteger roundUp(BigInteger bigInteger) {
        BigInteger[] divisionAndRemainder = bigInteger.divideAndRemainder(ROUND);
        BigInteger roundedValue = divisionAndRemainder[0].multiply(ROUND);
        if (!divisionAndRemainder[1].equals(BigInteger.ZERO)) {
            roundedValue = roundedValue.add(ROUND);
        }
        return roundedValue;
    }



}
