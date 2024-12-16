package com.trove.gamifiedtrading.services;

import com.trove.gamifiedtrading.data.body.BaseResponse;
import com.trove.gamifiedtrading.data.dto.CreditWalletDto;
import com.trove.gamifiedtrading.data.dto.TransferFromWalletDto;

public interface IWalletService {
        BaseResponse creditFunds(CreditWalletDto creditWalletDto);
        BaseResponse transferFunds(TransferFromWalletDto transferFromWalletDto);
}
