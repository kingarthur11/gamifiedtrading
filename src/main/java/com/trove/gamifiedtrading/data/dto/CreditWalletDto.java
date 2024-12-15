package com.trove.gamifiedtrading.data.dto;

import java.math.BigDecimal;

public record CreditWalletDto(
      BigDecimal amount,
      Long userId
) {
}
