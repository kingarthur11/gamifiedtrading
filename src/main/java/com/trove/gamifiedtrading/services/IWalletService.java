package com.trove.gamifiedtrading.services;

import com.trove.gamifiedtrading.data.body.ApiResponse;
import com.trove.gamifiedtrading.data.body.BaseResponse;
import com.trove.gamifiedtrading.data.dto.CreditWalletDto;
import com.trove.gamifiedtrading.data.dto.TransferFromWalletDto;
import com.trove.gamifiedtrading.entity.WalletEntity;

import java.util.List;
import java.util.Optional;

public interface IWalletService {
        ApiResponse<List<WalletEntity>> getAllWallets();
        ApiResponse<Optional<WalletEntity>> getWalletById(Long id);
        BaseResponse creditFunds(CreditWalletDto creditWalletDto);
        BaseResponse transferFunds(TransferFromWalletDto transferFromWalletDto);
}
