package ru.fastdelivery.domain.delivery.distanceCalc;

public interface DistanceProvider {

    Range getLatitude();

    Range getLongitude();
}
