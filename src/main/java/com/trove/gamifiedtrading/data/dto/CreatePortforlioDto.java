package com.trove.gamifiedtrading.data.dto;

public record CreatePortforlioDto(
    Long assetId,
    Integer quantity,
    Integer price,
    Long userId
) {

}
