package com.trove.gamifiedtrading.data.dto;

import java.math.BigDecimal;

public record ConvertAssetValue(
        String username,
        Integer quantity,
        String assetName,
        BigDecimal value,
        BigDecimal assetPrice
) {
}
