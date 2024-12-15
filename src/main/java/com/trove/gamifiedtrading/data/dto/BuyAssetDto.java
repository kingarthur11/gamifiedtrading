package com.trove.gamifiedtrading.data.dto;

public record BuyAssetDto(
        Integer quantity,
        Long portfolioId,
        Long userId
) {
}
