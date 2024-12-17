package com.trove.gamifiedtrading.services.implement;

import com.trove.gamifiedtrading.data.body.ApiResponse;
import com.trove.gamifiedtrading.data.body.BaseResponse;
import com.trove.gamifiedtrading.data.dto.CreditWalletDto;
import com.trove.gamifiedtrading.data.dto.TransferFromWalletDto;
import com.trove.gamifiedtrading.entity.UserEntity;
import com.trove.gamifiedtrading.entity.WalletEntity;
import com.trove.gamifiedtrading.repository.WalletRepository;
import com.trove.gamifiedtrading.services.IWalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class WalletService implements IWalletService {

    private final WalletRepository walletRepository;

    WalletService(WalletRepository walletRepository){
        this.walletRepository = walletRepository;
    }

    @Override
    public ApiResponse<List<WalletEntity>> getAllWallets() {
        var response = new ApiResponse<List<WalletEntity>>();
        List<WalletEntity> wallets = walletRepository.findAll();

        response.setStatus("success");
        response.setMessage("Wallets retrieved successfully.");
        response.setCode(200);
        response.setResult(wallets);

        return response;
    }

    @Override
    public ApiResponse<Optional<WalletEntity>> getWalletById(Long id) {
        var response = new ApiResponse<Optional<WalletEntity>>();
        Optional<WalletEntity> walletEntity = walletRepository.findById(id);

        if (walletEntity.isPresent()) {
            response.setStatus("success");
            response.setMessage("Wallet retrieved successfully.");
            response.setCode(200);
            response.setResult(walletEntity);
        } else {
            response.setStatus("error");
            response.setMessage("Wallet not found.");
            response.setCode(404);
            response.setResult(Optional.empty());
        }

        return response;
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
        walletRepository.save(walletEntity);

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

        log.info("before funds " + fromWalletEntity.getBalance());
        log.info("before funds " + transferFromWalletDto.amount());

        if (fromWalletEntity.getBalance().compareTo(transferFromWalletDto.amount()) < 0) {
            response.setCode(400);
            response.setStatus("error");
            response.setMessage("Insufficient balance to transfer.");

            return response;
        }

        BigDecimal toNewWalletBalance = toWalletEntity.getBalance().add(transferFromWalletDto.amount());
        BigDecimal fromNewWalletBalance = fromWalletEntity.getBalance().subtract(transferFromWalletDto.amount());

        toWalletEntity.setBalance(toNewWalletBalance);
        walletRepository.save(toWalletEntity);

        fromWalletEntity.setBalance(fromNewWalletBalance);
        walletRepository.save(fromWalletEntity);

        response.setCode(200);
        response.setStatus("success");
        response.setMessage("Funds transferred successfully");

        return response;
    }
}
