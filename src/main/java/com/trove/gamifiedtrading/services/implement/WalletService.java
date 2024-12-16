package com.trove.gamifiedtrading.services.implement;

import com.trove.gamifiedtrading.data.body.BaseResponse;
import com.trove.gamifiedtrading.data.dto.CreditWalletDto;
import com.trove.gamifiedtrading.data.dto.TransferFromWalletDto;
import com.trove.gamifiedtrading.entity.WalletEntity;
import com.trove.gamifiedtrading.repository.WalletRepository;
import com.trove.gamifiedtrading.services.IWalletService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletService implements IWalletService {

    private final WalletRepository walletRepository;

    WalletService(WalletRepository walletRepository){
        this.walletRepository = walletRepository;
    }

    @Override
    public BaseResponse creditFunds(CreditWalletDto creditWalletDto) {
        var response = new BaseResponse();
        Optional<WalletEntity> walletEntityOpt = walletRepository.findByUserId(creditWalletDto.userId());

        if (walletEntityOpt.isEmpty()) {
            response.setCode(404);
            response.setStatus("error");
            response.setMessage("Wallet not found.");
            return response;
        }

        WalletEntity walletEntity = walletEntityOpt.get();

        BigDecimal newWalletBalance = walletEntity.getBalance().add(creditWalletDto.amount());

        walletEntity.setBalance(newWalletBalance);

        response.setCode(200);
        response.setStatus("success");
        response.setMessage("Wallet credited successfully");

        return response;
    }

    @Override
    public BaseResponse transferFunds(TransferFromWalletDto transferFromWalletDto) {
        var response = new BaseResponse();
        Optional<WalletEntity> fromWalletEntityOpt = walletRepository.findByUserId(transferFromWalletDto.fromUserId());
        Optional<WalletEntity> toWalletEntityOpt = walletRepository.findByUserId(transferFromWalletDto.toUserId());

        if (fromWalletEntityOpt.isEmpty() || toWalletEntityOpt.isEmpty()) {
            response.setCode(404);
            response.setStatus("error");
            response.setMessage("Wallet not found.");
            return response;
        }

        WalletEntity fromWalletEntity = fromWalletEntityOpt.get();
        WalletEntity toWalletEntity = toWalletEntityOpt.get();

        BigDecimal toNewWalletBalance = toWalletEntity.getBalance().add(transferFromWalletDto.amount());
        BigDecimal fromNewWalletBalance = fromWalletEntity.getBalance().subtract(transferFromWalletDto.amount());

        if (fromNewWalletBalance.compareTo(transferFromWalletDto.amount()) < 0) {
            response.setCode(400);
            response.setStatus("error");
            response.setMessage("Insufficient balance to transfer.");
        }

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
