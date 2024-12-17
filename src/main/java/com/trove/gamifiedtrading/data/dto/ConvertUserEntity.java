package com.trove.gamifiedtrading.data.dto;

public record ConvertUserEntity(
    String username,
    Integer rank,
    Integer gemCount
) {
}
