package com.trove.gamifiedtrading.data.dto;

import java.math.BigDecimal;

public record TransferFromWalletDto(
      BigDecimal amount,
      Long toUserId,
      Long fromUserId
) {
}
