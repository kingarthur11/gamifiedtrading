package com.trove.gamifiedtrading.services.implement;

import com.trove.gamifiedtrading.data.body.BaseResponse;
import com.trove.gamifiedtrading.data.dto.UpdateWalletDto;
import com.trove.gamifiedtrading.entity.PortfolioEntity;
import com.trove.gamifiedtrading.entity.WalletEntity;
import com.trove.gamifiedtrading.repository.WalletRepository;
import com.trove.gamifiedtrading.services.IWalletService;

import java.math.BigDecimal;
import java.util.Optional;

public class WalletService implements IWalletService {

    private final WalletRepository walletRepository;

    WalletService(WalletRepository walletRepository){
        this.walletRepository = walletRepository;
    }

    @Override
    public BaseResponse creditFunds(UpdateWalletDto updateWalletDto) {
        var response = new BaseResponse();
        Optional<WalletEntity> walletEntityOpt = walletRepository.findByUserId(updateWalletDto.userId());
        WalletEntity walletEntity = walletEntityOpt.get();

        BigDecimal newWalletBalance = walletEntity.getBalance().add(updateWalletDto.amount());

        walletEntity.setBalance(newWalletBalance);

        response.setCode(200);
        response.setStatus("success");
        response.setMessage("Wallet credited successfully");

        return response;
    }

    @Override
    public BaseResponse transferFunds(UpdateWalletDto updateWalletDto) {
        var response = new BaseResponse();
        Optional<WalletEntity> fromWalletEntityOpt = walletRepository.findByUserId(updateWalletDto.userId());
        WalletEntity fromWalletEntity = fromWalletEntityOpt.get();

        Optional<WalletEntity> toWalletEntityOpt = walletRepository.findByUserId(updateWalletDto.userId());
        WalletEntity toWalletEntity = toWalletEntityOpt.get();

        BigDecimal toNewWalletBalance = toWalletEntity.getBalance().add(updateWalletDto.amount());
        BigDecimal fromNewWalletBalance = fromWalletEntity.getBalance().subtract(updateWalletDto.amount());

        toWalletEntity.setBalance(toNewWalletBalance);
        walletRepository.save(toWalletEntity);

        fromWalletEntity.setBalance(fromNewWalletBalance);
        walletRepository.save(fromWalletEntity);

        response.setCode(200);
        response.setStatus("success");
        response.setMessage("Wallet credited successfully");

        return response;
    }
}
