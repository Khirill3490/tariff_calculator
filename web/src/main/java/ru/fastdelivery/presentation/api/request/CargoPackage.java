package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigInteger;

public record CargoPackage(
        @Schema(description = "Вес упаковки, граммы", example = "5667.45")
        BigInteger weight,

        @Schema(description = "Длина упаковки, мм", example = "954")
        BigInteger length,
        @Schema(description = "Ширина упаковки, мм", example = "955")
        BigInteger width,
        @Schema(description = "Высота упаковки, мм", example = "956")
        BigInteger height
) {
}
