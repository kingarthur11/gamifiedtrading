package com.trove.gamifiedtrading.data.dto;

public record ConvertUserEntity(
    String username,
    Long rank,
    Integer gemCount
) {
}
