package ru.fastdelivery.domain.common.dimension;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LengthTest {

    @Test
    @DisplayName("Попытка создать отрицательный размер -> исключение")
    void whenParamBelowZero_thenException() {
        var param = BigInteger.valueOf(-1);
        assertThatThrownBy(() -> new Length(param))
                .isInstanceOf(IllegalArgumentException.class);
    }

//    @Test
//    @DisplayName("Попытка создать null -> исключение")
//    void whenNull_thenException() {
//        assertThatThrownBy(() -> new Length(null))
//                .isInstanceOf(IllegalArgumentException.class);
//    }

}
