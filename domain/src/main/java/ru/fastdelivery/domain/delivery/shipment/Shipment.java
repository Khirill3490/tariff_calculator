package ru.fastdelivery.domain.delivery.shipment;

import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.dimension.Dimension;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;

import java.math.BigDecimal;
import java.util.List;

/**
 * @param packages упаковки в грузе
 * @param currency валюта объявленная для груза
 */
public record Shipment(
        List<Pack> packages,
        Currency currency
) {
    public Weight weightAllPackages() {
        return packages.stream()
                .map(Pack::weight)
                .reduce(Weight.zero(), Weight::add);
    }

    public BigDecimal sizeAllPackages() {
        return packages.stream()
                .map(pack -> new Dimension(
                        pack.length(),
                        pack.width(),
                        pack.height()))
                .map(d -> d.roundDim(d))
                .map(d -> d.getSizeByParameters(d))
                .reduce(Dimension.zero(), BigDecimal::add);
    }
}
