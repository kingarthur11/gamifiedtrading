package com.trove.gamifiedtrading.services.implement;

import com.trove.gamifiedtrading.data.body.BaseResponse;
import com.trove.gamifiedtrading.data.dto.BuyAssetDto;
import com.trove.gamifiedtrading.entity.AssetEntity;
import com.trove.gamifiedtrading.entity.PortfolioEntity;
import com.trove.gamifiedtrading.entity.UserEntity;
import com.trove.gamifiedtrading.entity.WalletEntity;
import com.trove.gamifiedtrading.repository.AssetRepository;
import com.trove.gamifiedtrading.repository.PortfolioRepository;
import com.trove.gamifiedtrading.repository.UserRepository;
import com.trove.gamifiedtrading.repository.WalletRepository;
import com.trove.gamifiedtrading.services.ITradingService;

import java.math.BigDecimal;
import java.util.Optional;

public class TradingService implements ITradingService {

    private final PortfolioRepository portfolioRepository;
    private final AssetRepository assetRepository;
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    TradingService(PortfolioRepository portfolioRepository,
                   AssetRepository assetRepository,
                   UserRepository userRepository,
                   WalletRepository walletRepository) {
        this.portfolioRepository = portfolioRepository;
        this.assetRepository = assetRepository;
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }
    @Override
    public BaseResponse buyAsset(BuyAssetDto buyAssetDto) {
        var response = new BaseResponse();
        Optional<PortfolioEntity> portfolioEntityOpt = portfolioRepository.findByUserId(buyAssetDto.userId());

        if (portfolioEntityOpt.isEmpty()) {
            System.out.println("Portfolio not found.");
            response.setCode(500);
            response.setStatus("success");
            response.setMessage("Portfolio not found.");
            return response;
        }

        PortfolioEntity portfolioEntity = portfolioEntityOpt.get();

        Optional<WalletEntity> walletEntityOpt = walletRepository.findById(portfolioEntity.getUserId());
        Optional<AssetEntity> assetEntityOpt = assetRepository.findById(portfolioEntity.getAssetId());

        if (walletEntityOpt.isEmpty() || assetEntityOpt.isEmpty()) {
            response.setCode(500);
            response.setStatus("success");
            response.setMessage("Wallet or Asset information is missing.");
            return response;
        }

        WalletEntity walletEntity = walletEntityOpt.get();
        AssetEntity assetEntity = assetEntityOpt.get();

        BigDecimal walletBalance = walletEntity.getBalance();
        BigDecimal assetPrice = assetEntity.getPrice();

        if (walletBalance.compareTo(assetPrice) >= 0) {

            BigDecimal newBalance = walletBalance.subtract(assetPrice);
            walletEntity.setBalance(newBalance);
            walletRepository.save(walletEntity);

            portfolioEntity.setQuantity(buyAssetDto.quantity());
            portfolioRepository.save(portfolioEntity);

            Optional<UserEntity> userEntityOpt = userRepository.findById(buyAssetDto.userId());
            UserEntity userEntity = userEntityOpt.get();
            Integer userGem = userEntity.getGemCount();
            Integer newUserGem = userGem + 1;
            userEntity.setGemCount(newUserGem);

            System.out.println("Asset purchased successfully.");
            response.setCode(200);
            response.setStatus("success");
            response.setMessage("Asset purchased successfully.");

            return response;
        } else {
            System.out.println("Insufficient balance to purchase the asset.");
            response.setCode(500);
            response.setStatus("success");
            response.setMessage("Insufficient balance to purchase the asset.");
            return response;
        }
    }


    @Override
    public BaseResponse sellAsset(BuyAssetDto buyAssetDto) {
        var response = new BaseResponse();
        Optional<PortfolioEntity> portfolioEntityOpt = portfolioRepository.findById(buyAssetDto.portfolioId());

        if (portfolioEntityOpt.isEmpty()) {
            System.out.println("Portfolio not found.");
            response.setCode(500);
            response.setStatus("success");
            response.setMessage("Portfolio not found.");
            return response;
        }

        PortfolioEntity portfolioEntity = portfolioEntityOpt.get();

        Optional<WalletEntity> walletEntityOpt = walletRepository.findById(portfolioEntity.getUserId());
        if (walletEntityOpt.isEmpty()) {
            System.out.println("Wallet not found.");
            response.setCode(500);
            response.setStatus("success");
            response.setMessage("Wallet not found.");
            return response;
        }

        WalletEntity walletEntity = walletEntityOpt.get();

        Optional<AssetEntity> assetEntityOpt = assetRepository.findById(portfolioEntity.getAssetId());
        if (assetEntityOpt.isEmpty()) {
            System.out.println("Asset information is missing.");
            response.setCode(500);
            response.setStatus("success");
            response.setMessage("Asset information is missing.");
            return response;
        }

        AssetEntity assetEntity = assetEntityOpt.get();
        BigDecimal assetPrice = assetEntity.getPrice();

        Integer quantityToSell = buyAssetDto.quantity();
        Integer portfolioQuantity = portfolioEntity.getQuantity();

        if (quantityToSell > portfolioQuantity) {
            System.out.println("Not enough assets to sell.");
            response.setCode(500);
            response.setStatus("success");
            response.setMessage("Not enough assets to sell.");
            return response;
        }

        BigDecimal totalSalePrice = assetPrice.multiply(new BigDecimal(quantityToSell));
        BigDecimal newWalletBalance = walletEntity.getBalance().add(totalSalePrice);

        walletEntity.setBalance(newWalletBalance);
        walletRepository.save(walletEntity);

        Integer newPortfolioQuantity = portfolioQuantity - quantityToSell;
        portfolioEntity.setQuantity(newPortfolioQuantity);
        portfolioRepository.save(portfolioEntity);

        Optional<UserEntity> userEntityOpt = userRepository.findById(buyAssetDto.userId());
        UserEntity userEntity = userEntityOpt.get();
        Integer userGem = userEntity.getGemCount();
        Integer newUserGem = userGem + 1;
        userEntity.setGemCount(newUserGem);

        response.setCode(200);
        response.setStatus("success");
        response.setMessage("Asset sold successfully");
        System.out.println("Assets sold successfully.");

        return response;
    }

}
