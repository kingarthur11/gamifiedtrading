package com.trove.gamifiedtrading.data.dto;

import jakarta.persistence.Column;

import java.math.BigDecimal;

public record CreateAssetDto(
    String name,
    BigDecimal price
) {
}
