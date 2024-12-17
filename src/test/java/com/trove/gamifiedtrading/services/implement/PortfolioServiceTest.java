package com.trove.gamifiedtrading.services.implement;

import com.trove.gamifiedtrading.data.body.BaseResponse;
import com.trove.gamifiedtrading.data.dto.CreatePortforlioDto;
import com.trove.gamifiedtrading.entity.AssetEntity;
import com.trove.gamifiedtrading.entity.PortfolioEntity;
import com.trove.gamifiedtrading.repository.AssetRepository;
import com.trove.gamifiedtrading.repository.PortfolioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PortfolioServiceTest {

    @Mock
    private PortfolioRepository portfolioRepository;

    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private PortfolioService portfolioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddAsset_Success() {
        CreatePortforlioDto createPortfolioDto = new CreatePortforlioDto(100L, 1L);

        AssetEntity assetEntity = new AssetEntity();
        assetEntity.setId(100L);
        assetEntity.setName("Gold");

        when(assetRepository.findById(100L)).thenReturn(Optional.of(assetEntity));
        when(portfolioRepository.findByUserId(1L)).thenReturn(Optional.empty());

        BaseResponse response = portfolioService.addAsset(createPortfolioDto);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getStatus());
        assertEquals("Asset added to portfolio successfully.", response.getMessage());

        verify(assetRepository, times(1)).findById(100L);
        verify(portfolioRepository, times(1)).findByUserId(1L);
        verify(portfolioRepository, times(1)).save(any(PortfolioEntity.class));
    }


    @Test
    void testAddAsset_AlreadyExists() {
        // Arrange
        CreatePortforlioDto createPortfolioDto = new CreatePortforlioDto(1L, 1L);
        PortfolioEntity existingPortfolio = new PortfolioEntity();
        existingPortfolio.setAssetId(100L);

        when(portfolioRepository.findByUserId(1L)).thenReturn(Optional.of(existingPortfolio));

        // Act
        BaseResponse response = portfolioService.addAsset(createPortfolioDto);

        // Assert
        assertEquals(404, response.getCode());
        assertEquals("error", response.getStatus());
        assertEquals("Asset not found.", response.getMessage());

        verify(portfolioRepository, never()).save(any(PortfolioEntity.class));
    }

    @Test
    void testAddAsset_AssetNotFound() {
        // Arrange
        CreatePortforlioDto createPortfolioDto = new CreatePortforlioDto(1L, 1L);

        when(portfolioRepository.findByUserId(1L)).thenReturn(Optional.empty());
        when(assetRepository.findById(100L)).thenReturn(Optional.empty());

        // Act
        BaseResponse response = portfolioService.addAsset(createPortfolioDto);

        // Assert
        assertEquals(404, response.getCode());
        assertEquals("error", response.getStatus());
        assertEquals("Asset not found.", response.getMessage());

        verify(portfolioRepository, never()).save(any(PortfolioEntity.class));
    }

    @Test
    void testRemoveAsset_Success() {
        CreatePortforlioDto createPortfolioDto = new CreatePortforlioDto(100L, 1L);
        PortfolioEntity existingPortfolio = new PortfolioEntity();
        existingPortfolio.setId(1L);
        existingPortfolio.setAssetId(100L);

        AssetEntity assetEntity = new AssetEntity();
        assetEntity.setId(100L);
        assetEntity.setName("Gold");

        when(portfolioRepository.findByUserId(1L)).thenReturn(Optional.of(existingPortfolio));
        when(assetRepository.findById(100L)).thenReturn(Optional.of(assetEntity));

        // Act
        BaseResponse response = portfolioService.removeAsset(createPortfolioDto);

        // Assert
        assertEquals(200, response.getCode());
        assertEquals("success", response.getStatus());
        assertEquals("Asset removed successfully", response.getMessage());

        verify(portfolioRepository, times(1)).deleteById(existingPortfolio.getId());
    }

    @Test
    void testRemoveAsset_PortfolioDoesNotExist() {
        // Arrange
        CreatePortforlioDto createPortfolioDto = new CreatePortforlioDto(100L, 1L);
        AssetEntity assetEntity = new AssetEntity();
        assetEntity.setId(100L);
        assetEntity.setName("Gold");

        when(assetRepository.findById(100L)).thenReturn(Optional.of(assetEntity));
        when(portfolioRepository.findByUserId(1L)).thenReturn(Optional.empty());

        // Act
        BaseResponse response = portfolioService.removeAsset(createPortfolioDto);

        // Assert
        assertEquals(404, response.getCode());
        assertEquals("error", response.getStatus());
        assertEquals("Portfolio does not exists for the given asset.", response.getMessage());

        verify(portfolioRepository, never()).deleteById(anyLong());
    }

    @Test
    void testRemoveAsset_AssetNotFound() {
        CreatePortforlioDto createPortfolioDto = new CreatePortforlioDto(1L, 1L);  // assetId should be 100

        PortfolioEntity existingPortfolio = new PortfolioEntity();
        existingPortfolio.setId(1L);
        existingPortfolio.setAssetId(100L);
        existingPortfolio.setUserId(1L);

        // Mock the portfolioRepository and assetRepository
        when(portfolioRepository.findByUserId(1L)).thenReturn(Optional.of(existingPortfolio));
        when(assetRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        BaseResponse response = portfolioService.removeAsset(createPortfolioDto);

        // Assert
        assertEquals(404, response.getCode());
        assertEquals("error", response.getStatus());
        assertEquals("Asset not found.", response.getMessage());

        verify(assetRepository, times(1)).findById(1L);
        verify(portfolioRepository, never()).findByUserId(anyLong());
        verify(portfolioRepository, never()).deleteById(anyLong());
    }


}