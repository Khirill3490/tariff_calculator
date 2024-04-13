package ru.fastdelivery.domain.delivery.pack;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.dimension.Dimension;
import ru.fastdelivery.domain.common.dimension.Length;
import ru.fastdelivery.domain.common.weight.Weight;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PackTest {

    @Test
    @DisplayName("Вес больше максимально допустимого. Исключение")
    void whenWeightMoreThanMaxWeight_thenThrowException() {
        var weight = new Weight(BigInteger.valueOf(150_001));
        var dimension = new Dimension(new Length(BigInteger.valueOf(1_500)),
                new Length(BigInteger.valueOf(1_500)),
                new Length(BigInteger.valueOf(1_500)));
        assertThatThrownBy(() -> new Pack(weight,
                new Length(BigInteger.valueOf(1_500)),
                new Length(BigInteger.valueOf(1_500)),
                new Length(BigInteger.valueOf(1_500))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Вес меньше максимально допустимого. Ок")
    void whenWeightLessThanMaxWeight_thenObjectCreated() {
        var actual = new Pack(new Weight(BigInteger.valueOf(1_000)),
                new Length(BigInteger.valueOf(1_500)),
                new Length(BigInteger.valueOf(1_500)),
                new Length(BigInteger.valueOf(1_500)));
        assertThat(actual.weight()).isEqualTo(new Weight(BigInteger.valueOf(1_000)));
    }

    @Test
    @DisplayName("Габариты больше максимально допустимых. Исключение")
    void whenOnyOfSizeMoreThanMaxEnableSize_thenThrowException() {
        var weight = new Weight(BigInteger.valueOf(1500));
        var dimension = new Dimension(new Length(BigInteger.valueOf(1_501)),
                new Length(BigInteger.valueOf(1_500)),
                new Length(BigInteger.valueOf(1_500)));
        assertThatThrownBy(() -> new Pack(weight,
                new Length(BigInteger.valueOf(1_501)),
                new Length(BigInteger.valueOf(1_500)),
                new Length(BigInteger.valueOf(1_500))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Габариты не превышают максимально допустимых. Ок")
    void whenSizeParamsLessThanMaxEnableSize_thenThrowException() {
        var actual = new Pack(new Weight(BigInteger.valueOf(1_000)),
                new Length(BigInteger.valueOf(1_500)),
                new Length(BigInteger.valueOf(1_500)),
                new Length(BigInteger.valueOf(1_500)));
        assertThat(actual.weight()).isEqualTo(new Weight(BigInteger.valueOf(1_000)));
    }
}