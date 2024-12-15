package com.trove.gamifiedtrading.data.dto;

public record CreateUserDto(
    String username,
    String gemCount,
    String rank
) {

}
