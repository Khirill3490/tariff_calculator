package ru.fastdelivery.domain.delivery.pack;

import ru.fastdelivery.domain.common.dimension.Length;
import ru.fastdelivery.domain.common.weight.Weight;

import java.math.BigInteger;

/**
 * Упаковка груза
 *
 * @param weight вес товаров в упаковке
 */
public record Pack(Weight weight,
                   Length length,
                   Length width,
                   Length height) {

    private static final Weight maxWeight = new Weight(BigInteger.valueOf(150_000));
    private static final Length MAX_VALUE = new Length(BigInteger.valueOf(1_500));

    public Pack {
        if (weight.greaterThan(maxWeight)) {
            throw new IllegalArgumentException("Package can't be more than " + maxWeight);
        } if (length.greaterThan(MAX_VALUE)) {
            throw new IllegalArgumentException("Size param can't be more than " + MAX_VALUE);
        } if (width.greaterThan(MAX_VALUE)) {
            throw new IllegalArgumentException("Size param can't be more than " + MAX_VALUE);
        } if (height.greaterThan(MAX_VALUE)) {
            throw new IllegalArgumentException("Size param can't be more than " + MAX_VALUE);
        }
    }
}
