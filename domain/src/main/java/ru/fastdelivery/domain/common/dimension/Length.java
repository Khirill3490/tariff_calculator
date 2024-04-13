package ru.fastdelivery.domain.common.dimension;

import java.math.BigInteger;

public record Length(
        BigInteger param
) {
    private static final BigInteger ROUND = new BigInteger("50");

    public Length {
        if (isParamLessOrEqualsZero(param)) {
            throw new IllegalArgumentException("The parameter cannot be below or equals Zero");
        }
    }

    private boolean isParamLessOrEqualsZero(BigInteger param) {
        return param.compareTo(BigInteger.ZERO) < 0;
    }

    public boolean greaterThan(Length a) {
        return param().compareTo(a.param()) > 0;
    }

    public Length getRoundLength(Length length) {
        return new Length(length.roundUp(length.param));
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
