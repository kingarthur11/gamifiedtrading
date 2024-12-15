package com.trove.gamifiedtrading.services;

import com.trove.gamifiedtrading.data.body.BaseResponse;
import com.trove.gamifiedtrading.data.dto.UpdateWalletDto;

public interface IWalletService {
        BaseResponse creditFunds(UpdateWalletDto updateWalletDto);
        BaseResponse transferFunds(UpdateWalletDto updateWalletDto);
}
