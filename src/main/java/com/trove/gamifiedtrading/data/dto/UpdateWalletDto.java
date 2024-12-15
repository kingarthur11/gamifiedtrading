package com.trove.gamifiedtrading.data.dto;

import java.math.BigDecimal;

public record UpdateWalletDto(
      BigDecimal amount,
      Long userId
) {
}
