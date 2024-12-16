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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TradingServiceTest {

    @Mock
    private PortfolioRepository portfolioRepository;

    @Mock
    private AssetRepository assetRepository;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TradingService tradingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuyAsset_Success() {
        // Arrange
        BuyAssetDto buyAssetDto = new BuyAssetDto(1, 100L, 1L);
        PortfolioEntity portfolioEntity = new PortfolioEntity(1L, "Gold", 100, 1L, 1L);
        AssetEntity assetEntity = new AssetEntity();
        assetEntity.setId(1L);
        assetEntity.setPrice(new BigDecimal("50.00"));
        assetEntity.setCount(10);

        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setId(1L);
        walletEntity.setBalance(new BigDecimal("200.00"));
        walletEntity.setUserId(1L);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setGemCount(5);
        userEntity.setNumMilestone(1);
        userEntity.setBuyCount(2);
        userEntity.setSellCount(1);

        when(portfolioRepository.findByUserId(1L)).thenReturn(Optional.of(portfolioEntity));
        when(assetRepository.findById(1L)).thenReturn(Optional.of(assetEntity));
        when(walletRepository.findByUserId(1L)).thenReturn(Optional.of(walletEntity));
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        // Act
        BaseResponse response = tradingService.buyAsset(buyAssetDto);

        // Assert
        assertEquals(200, response.getCode());
        assertEquals("success", response.getStatus());
        assertEquals("Asset purchased successfully.", response.getMessage());

        verify(walletRepository, times(1)).save(walletEntity);
        verify(portfolioRepository, times(1)).save(portfolioEntity);
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void testBuyAsset_InsufficientBalance() {
        // Arrange
        BuyAssetDto buyAssetDto = new BuyAssetDto(5, 1L, 1L);
        PortfolioEntity portfolioEntity = new PortfolioEntity(1L, "Gold", 100, 1L, 1L);
        AssetEntity assetEntity = new AssetEntity();
        assetEntity.setId(1L);
        assetEntity.setPrice(new BigDecimal("100.00"));
        assetEntity.setCount(10);

        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setId(1L);
        walletEntity.setBalance(new BigDecimal("150.00"));
        walletEntity.setUserId(1L);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setGemCount(5);
        userEntity.setNumMilestone(1);
        userEntity.setBuyCount(2);
        userEntity.setSellCount(1);

        when(portfolioRepository.findByUserId(1L)).thenReturn(Optional.of(portfolioEntity));
        when(assetRepository.findById(1L)).thenReturn(Optional.of(assetEntity));
        when(walletRepository.findByUserId(1L)).thenReturn(Optional.of(walletEntity));
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        // Act
        BaseResponse response = tradingService.buyAsset(buyAssetDto);

        // Assert
        assertEquals(400, response.getCode());
        assertEquals("error", response.getStatus());
        assertEquals("Insufficient balance to purchase the asset.", response.getMessage());

    }

    @Test
    void testBuyAsset_PortfolioNotFound() {
        // Arrange
        BuyAssetDto buyAssetDto = new BuyAssetDto(1, 100L, 2L);
        when(portfolioRepository.findByUserId(1L)).thenReturn(Optional.empty());

        // Act
        BaseResponse response = tradingService.buyAsset(buyAssetDto);

        // Assert
        assertEquals(404, response.getCode());
        assertEquals("error", response.getStatus());
        assertEquals("Portfolio not found.", response.getMessage());
        verify(walletRepository, never()).save(any());
    }

    @Test
    void testSellAsset_Success() {
        // Arrange
        BuyAssetDto buyAssetDto = new BuyAssetDto(1, 1L, 1L);

        PortfolioEntity portfolioEntity = new PortfolioEntity(1L, "Gold", 100, 1L, 1L);
        AssetEntity assetEntity = new AssetEntity();
        assetEntity.setId(1L);
        assetEntity.setPrice(new BigDecimal("50.00"));
        assetEntity.setCount(10);

        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setId(1L);
        walletEntity.setBalance(new BigDecimal("200.00"));
        walletEntity.setUserId(1L);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setGemCount(5);
        userEntity.setNumMilestone(1);
        userEntity.setBuyCount(2);
        userEntity.setSellCount(1);

        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(portfolioEntity));
        when(assetRepository.findById(1L)).thenReturn(Optional.of(assetEntity));
        when(walletRepository.findByUserId(1L)).thenReturn(Optional.of(walletEntity));
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        // Act
        BaseResponse response = tradingService.sellAsset(buyAssetDto);

        // Assert
        assertEquals(200, response.getCode());
        assertEquals("success", response.getStatus());
        assertEquals("Asset sold successfully", response.getMessage());

        verify(walletRepository, times(1)).save(walletEntity);
        verify(portfolioRepository, times(1)).save(portfolioEntity);
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void testSellAsset_NotEnoughAssets() {
        // Arrange
        BuyAssetDto buyAssetDto = new BuyAssetDto(100, 1L, 2L);

        PortfolioEntity portfolioEntity = new PortfolioEntity(1L, "Gold", 10, 2L, 1L);
        AssetEntity assetEntity = new AssetEntity();
        assetEntity.setId(1L);
        assetEntity.setPrice(new BigDecimal("50.00"));
        assetEntity.setCount(10);

        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setId(1L);
        walletEntity.setBalance(new BigDecimal("200.00"));
        walletEntity.setUserId(2L);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setGemCount(5);
        userEntity.setNumMilestone(1);
        userEntity.setBuyCount(2);
        userEntity.setSellCount(1);

        when(portfolioRepository.findById(1L)).thenReturn(Optional.of(portfolioEntity));
        when(assetRepository.findById(1L)).thenReturn(Optional.of(assetEntity));
        when(walletRepository.findByUserId(2L)).thenReturn(Optional.of(walletEntity));
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        // Act
        BaseResponse response = tradingService.sellAsset(buyAssetDto);

        // Assert
        assertEquals(400, response.getCode());
        assertEquals("error", response.getStatus());
        assertEquals("Not enough assets to sell.", response.getMessage());
        verify(walletRepository, never()).save(any());
    }

    @Test
    void testSellAsset_PortfolioNotFound() {
        // Arrange
        BuyAssetDto buyAssetDto = new BuyAssetDto(1, 100L, 2L);
        buyAssetDto.portfolioId();

        when(portfolioRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        BaseResponse response = tradingService.sellAsset(buyAssetDto);

        // Assert
        assertEquals(404, response.getCode());
        assertEquals("error", response.getStatus());
        assertEquals("Portfolio not found.", response.getMessage());
        verify(walletRepository, never()).save(any());
    }
  
}