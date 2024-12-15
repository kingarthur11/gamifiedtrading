package com.trove.gamifiedtrading.data.dto;

public record CreateUserDto(
    String username,
    Integer gemCount,
    Integer rank
) {

}
