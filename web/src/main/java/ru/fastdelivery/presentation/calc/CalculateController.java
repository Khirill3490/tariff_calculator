package ru.fastdelivery.presentation.calc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.dimension.Length;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.distanceCalc.DistanceCalculator;
import ru.fastdelivery.domain.delivery.distanceCalc.DistanceFactory;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.presentation.api.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.api.response.CalculatePackagesResponse;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

@RestController
@RequestMapping("/api/v1/calculate/")
@RequiredArgsConstructor
@Tag(name = "Расчеты стоимости доставки")
public class CalculateController {
    private final TariffCalculateUseCase tariffCalculateUseCase;
    private final CurrencyFactory currencyFactory;
    private final DistanceFactory distanceFactory;

    @PostMapping
    @Operation(summary = "Расчет стоимости по упаковкам груза")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    public CalculatePackagesResponse calculate(
            @Valid @RequestBody CalculatePackagesRequest request) {
        var packsWeightsAndSize = request.packages().stream()
                .map(cargoPackage -> {
                    Weight weight = new Weight(cargoPackage.weight());
                    Length length = new Length(cargoPackage.length());
                    Length width = new Length(cargoPackage.width());
                    Length height = new Length(cargoPackage.height());
                    return new Pack(weight, length, width, height);
                })
                .toList();


        var departure = request.departure();
        var destination = request.destination();

        var getDistance = new DistanceCalculator(
                distanceFactory,
                departure.latitude(),
                departure.longitude(),
                destination.latitude(),
                destination.longitude()).calcDist();

        var shipment = new Shipment(packsWeightsAndSize, currencyFactory.create(request.currencyCode()));
        var calculatedPrice = tariffCalculateUseCase.calc(shipment, getDistance);
        var minimalPrice = tariffCalculateUseCase.minimalPrice();
        return new CalculatePackagesResponse(calculatedPrice, minimalPrice);
    }
}

