package com.trove.gamifiedtrading.services.implement;

import com.trove.gamifiedtrading.data.body.BaseResponse;
import com.trove.gamifiedtrading.data.dto.CreditWalletDto;
import com.trove.gamifiedtrading.data.dto.TransferFromWalletDto;
import com.trove.gamifiedtrading.entity.WalletEntity;
import com.trove.gamifiedtrading.repository.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletService walletService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void creditFunds_ShouldReturnSuccess_WhenWalletExists() {
        // Arrange
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setUserId(1L);
        walletEntity.setBalance(BigDecimal.valueOf(100));

        CreditWalletDto creditDto = new CreditWalletDto(BigDecimal.valueOf(50), 1L );

        when(walletRepository.findByUserId(1L)).thenReturn(Optional.of(walletEntity));

        // Act
        BaseResponse response = walletService.creditFunds(creditDto);

        // Assert
        assertEquals(200, response.getCode());
        assertEquals("success", response.getStatus());
        assertEquals("Wallet credited successfully", response.getMessage());
        assertEquals(BigDecimal.valueOf(150), walletEntity.getBalance());

        verify(walletRepository, times(1)).findByUserId(1L);
    }

    @Test
    void creditFunds_ShouldReturnError_WhenWalletNotFound() {
        // Arrange
        CreditWalletDto creditDto = new CreditWalletDto(BigDecimal.valueOf(50), 1L );

        when(walletRepository.findByUserId(1L)).thenReturn(Optional.empty());

        // Act
        BaseResponse response = walletService.creditFunds(creditDto);

        // Assert
        assertEquals(404, response.getCode());
        assertEquals("error", response.getStatus());
        assertEquals("Wallet not found.", response.getMessage());

        verify(walletRepository, times(1)).findByUserId(1L);
    }

    @Test
    void transferFunds_ShouldReturnSuccess_WhenWalletsExistAndSufficientBalance() {
        // Arrange
        WalletEntity fromWallet = new WalletEntity();
        fromWallet.setUserId(2L);
        fromWallet.setBalance(BigDecimal.valueOf(100));

        WalletEntity toWallet = new WalletEntity();
        toWallet.setUserId(1L);
        toWallet.setBalance(BigDecimal.valueOf(50));

        TransferFromWalletDto transferDto = new TransferFromWalletDto(BigDecimal.valueOf(30), 1L, 2L );

        when(walletRepository.findByUserId(2L)).thenReturn(Optional.of(fromWallet));
        when(walletRepository.findByUserId(1L)).thenReturn(Optional.of(toWallet));

        // Act
        BaseResponse response = walletService.transferFunds(transferDto);

        // Assert
        assertEquals(200, response.getCode());
        assertEquals("success", response.getStatus());
        assertEquals("Funds transferred successfully", response.getMessage());

        assertEquals(BigDecimal.valueOf(70), fromWallet.getBalance());
        assertEquals(BigDecimal.valueOf(80), toWallet.getBalance());

        verify(walletRepository, times(1)).findByUserId(1L);
        verify(walletRepository, times(1)).findByUserId(2L);
        verify(walletRepository, times(1)).save(fromWallet);
        verify(walletRepository, times(1)).save(toWallet);
    }

    @Test
    void transferFunds_ShouldReturnError_WhenWalletNotFound() {
        // Arrange
        TransferFromWalletDto transferDto = new TransferFromWalletDto(BigDecimal.valueOf(30), 1L, 2L );

        when(walletRepository.findByUserId(1L)).thenReturn(Optional.empty());

        // Act
        BaseResponse response = walletService.transferFunds(transferDto);

        // Assert
        assertEquals(404, response.getCode());
        assertEquals("error", response.getStatus());
        assertEquals("Wallet not found.", response.getMessage());

        verify(walletRepository, times(1)).findByUserId(1L);
    }

    @Test
    void transferFunds_ShouldReturnError_WhenInsufficientBalance() {
        // Arrange
        WalletEntity fromWallet = new WalletEntity();
        fromWallet.setUserId(2L);
        fromWallet.setBalance(BigDecimal.valueOf(20));

        WalletEntity toWallet = new WalletEntity();
        toWallet.setUserId(1L);
        toWallet.setBalance(BigDecimal.valueOf(50));

        TransferFromWalletDto transferDto = new TransferFromWalletDto(BigDecimal.valueOf(30), 1L, 2L );

        when(walletRepository.findByUserId(2L)).thenReturn(Optional.of(fromWallet));
        when(walletRepository.findByUserId(1L)).thenReturn(Optional.of(toWallet));

        // Act
        BaseResponse response = walletService.transferFunds(transferDto);

        // Assert
        assertEquals(400, response.getCode());
        assertEquals("error", response.getStatus());
        assertEquals("Insufficient balance to transfer.", response.getMessage());

        assertEquals(BigDecimal.valueOf(20), fromWallet.getBalance());
        assertEquals(BigDecimal.valueOf(50), toWallet.getBalance());

        verify(walletRepository, times(1)).findByUserId(1L);
        verify(walletRepository, times(1)).findByUserId(2L);
        verify(walletRepository, never()).save(any());
    }
}