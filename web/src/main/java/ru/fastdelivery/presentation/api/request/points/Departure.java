package ru.fastdelivery.presentation.api.request.points;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record Departure(
        @Schema(description = "Координаты широты", example = "55.446008")
        BigDecimal latitude,

        @Schema(description = "Координаты долготы", example = "65.339151")
        BigDecimal longitude
) {
}
