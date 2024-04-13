package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import javax.inject.Named;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {
    private final WeightPriceProvider weightPriceProvider;

    private static final BigDecimal BASIC_DISTANCE = BigDecimal.valueOf(450);

    public Price calc(Shipment shipment, BigDecimal dist) {
        var weightAllPackagesKg = shipment.weightAllPackages().kilograms();
        var sizeAllPackages = shipment.sizeAllPackages();
        var minimalPrice = weightPriceProvider.minimalPrice();

        var costByWeight = weightPriceProvider.costPerKg().multiply(weightAllPackagesKg);
        var costBySize = weightPriceProvider.costPerMeter().multiply(sizeAllPackages);
        var price = costByWeight.max(minimalPrice).max(costBySize);

//        System.out.println("Цена актуальная " + price.amount().setScale(4, RoundingMode.HALF_UP));
//        System.out.println("Цена за вес " + costByWeight.amount().setScale(4, RoundingMode.HALF_UP));
//        System.out.println("Цена размер " + costBySize.amount().setScale(4, RoundingMode.HALF_UP));

        if (dist.compareTo(BASIC_DISTANCE) > 0) {
            return costByDistance(price, dist);
        }
        return new Price(price.amount().setScale(4, RoundingMode.HALF_UP), price.currency());
    }

    public Price costByDistance(Price price, BigDecimal dist) {
        BigDecimal newCost = dist.divide(BASIC_DISTANCE,4, RoundingMode.HALF_UP).multiply(price.amount())
                .setScale(4, RoundingMode.HALF_UP);
        return new Price(newCost, price.currency());
    }

    public Price minimalPrice() {
        return weightPriceProvider.minimalPrice();
    }

}
