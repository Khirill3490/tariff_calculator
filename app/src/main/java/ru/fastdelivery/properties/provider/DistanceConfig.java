package ru.fastdelivery.properties.provider;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.fastdelivery.domain.delivery.distanceCalc.Range;
import ru.fastdelivery.domain.delivery.distanceCalc.DistanceProvider;

@ConfigurationProperties("coordinates")
@Data
public class DistanceConfig implements DistanceProvider {

    private final Range latitude;
    private final Range longitude;
}
