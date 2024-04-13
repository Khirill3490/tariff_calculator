package ru.fastdelivery.calc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.fastdelivery.ControllerTest;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.distanceCalc.DistanceFactory;
import ru.fastdelivery.presentation.api.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.api.request.CargoPackage;
import ru.fastdelivery.presentation.api.request.points.Departure;
import ru.fastdelivery.presentation.api.request.points.Destination;
import ru.fastdelivery.presentation.api.response.CalculatePackagesResponse;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j

class CalculateControllerTest extends ControllerTest {

    final String baseCalculateApi = "/api/v1/calculate/";
    @MockBean
    TariffCalculateUseCase useCase;
    @MockBean
    CurrencyFactory currencyFactory;
    @MockBean
    DistanceFactory distanceFactory;

    @Test
    @DisplayName("Валидные данные для расчета стоимость -> Ответ 200")
    void whenValidInputData_thenReturn200() {
        var request = new CalculatePackagesRequest(
                List.of(new CargoPackage(BigInteger.valueOf(10),
                        BigInteger.valueOf(10),
                        BigInteger.valueOf(10),
                        BigInteger.valueOf(10))),
                "RUB",
                new Destination(BigDecimal.valueOf(55), BigDecimal.valueOf(60)),
                new Departure(BigDecimal.valueOf(55), BigDecimal.valueOf(65)));
        var rub = new CurrencyFactory(code -> true).create("RUB");
        when(useCase.calc(any(), any())).thenReturn(new Price(BigDecimal.valueOf(10), rub));
        when(useCase.minimalPrice()).thenReturn(new Price(BigDecimal.valueOf(5), rub));
        when(distanceFactory.isValid(any(),any(),any(), any())).thenReturn(true);
        ResponseEntity<CalculatePackagesResponse> response =
                restTemplate.postForEntity(baseCalculateApi, request, CalculatePackagesResponse.class);
        System.out.println(response.toString());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Список упаковок == null -> Ответ 400")
    void whenEmptyListPackages_thenReturn400() {
        var request = new CalculatePackagesRequest(null, "RUB", null, null);

        ResponseEntity<String> response = restTemplate.postForEntity(baseCalculateApi, request, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

//    @Test
//    @DisplayName("Валидные данные для расчета стоимость -> Ответ 200")
//    void whenValidInputData_thenReturn20() {
//        var request = new CalculatePackagesRequest(
//                List.of(new CargoPackage(BigInteger.TEN, BigInteger.TEN, BigInteger.TEN, BigInteger.TEN)),
//                "RUB",
//                new Destination(BigDecimal.valueOf(55), BigDecimal.valueOf(60)),
//                new Departure(BigDecimal.valueOf(55), BigDecimal.valueOf(65)));
//        var rub = new CurrencyFactory(code -> true).create("RUB");
//        when(useCase.calc(any(), any())).thenReturn(new Price(BigDecimal.valueOf(10), rub));
//        when(useCase.minimalPrice()).thenReturn(new Price(BigDecimal.valueOf(5), rub));
//        when()
//
//        ResponseEntity<CalculatePackagesResponse> response =
//                restTemplate.postForEntity(baseCalculateApi, request, CalculatePackagesResponse.class);
//        System.out.println(response);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
}
