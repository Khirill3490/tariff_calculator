package ru.fastdelivery.domain.delivery.distance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.distanceCalc.DistanceCalculator;
import ru.fastdelivery.domain.delivery.distanceCalc.DistanceFactory;
import ru.fastdelivery.domain.delivery.distanceCalc.DistanceProvider;
import ru.fastdelivery.domain.delivery.distanceCalc.Range;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class DistanceCalculatorTest {

    DistanceProvider distanceProvider = Mockito.mock(DistanceProvider.class);
    DistanceFactory distanceFactory = new DistanceFactory(distanceProvider);




    @Test
    @DisplayName("Валидные точки на карте -> Ok")
    void whenPointsValid_thenOk() {
        BigDecimal a = BigDecimal.valueOf(10);
        BigDecimal b = BigDecimal.valueOf(20);
        BigDecimal c = BigDecimal.valueOf(30);
        BigDecimal d = BigDecimal.valueOf(40);

        when(distanceProvider.getLatitude()).thenReturn(new Range(BigDecimal.ZERO, BigDecimal.valueOf(50)));
        when(distanceProvider.getLongitude()).thenReturn(new Range(BigDecimal.ZERO, BigDecimal.valueOf(50)));

        var calc = new DistanceCalculator(distanceFactory, a, b, c, d);

        assertThat(calc.llat1()).isEqualByComparingTo(a);
        assertThat(calc.llong1()).isEqualByComparingTo(b);
        assertThat(calc.llat2()).isEqualByComparingTo(c);
        assertThat(calc.llong2()).isEqualByComparingTo(d);
    }

    @Test
    @DisplayName("Не валидные точки на карте -> Исключение")
    void whenPointsValid_thenThrowEx() {
        BigDecimal a = BigDecimal.valueOf(10);
        BigDecimal b = BigDecimal.valueOf(20);
        BigDecimal c = BigDecimal.valueOf(30);
        BigDecimal d = BigDecimal.valueOf(60);

        when(distanceProvider.getLatitude()).thenReturn(new Range(BigDecimal.ZERO, BigDecimal.valueOf(50)));
        when(distanceProvider.getLongitude()).thenReturn(new Range(BigDecimal.ZERO, BigDecimal.valueOf(50)));

        assertThatThrownBy(() -> new DistanceCalculator(distanceFactory, a, b, c, d))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
