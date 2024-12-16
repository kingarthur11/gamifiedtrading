package com.trove.gamifiedtrading.services.implement;

import com.trove.gamifiedtrading.data.body.BaseResponse;
import com.trove.gamifiedtrading.data.dto.CreateAssetDto;
import com.trove.gamifiedtrading.entity.AssetEntity;
import com.trove.gamifiedtrading.repository.AssetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssetServiceTest {

    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private AssetService assetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAsset() {
        // Arrange
        CreateAssetDto createAssetDto = new CreateAssetDto("Gold", new BigDecimal("1500.00"));
        ArgumentCaptor<AssetEntity> assetCaptor = ArgumentCaptor.forClass(AssetEntity.class);

        // Act
        BaseResponse response = assetService.createAsset(createAssetDto);

        // Assert
        verify(assetRepository, times(1)).save(assetCaptor.capture());

        AssetEntity savedEntity = assetCaptor.getValue();
        assertEquals("Gold", savedEntity.getName());
        assertEquals(new BigDecimal("1500.00"), savedEntity.getPrice());

        assertEquals(200, response.getCode());
        assertEquals("success", response.getStatus());
        assertEquals("Asset credited successfully", response.getMessage());
    }

    @Test
    void testUpdateAsset() {
        // Arrange
        Long assetId = 1L;
        CreateAssetDto createAssetDto = new CreateAssetDto("Silver", new BigDecimal("2000.00"));

        AssetEntity existingAsset = new AssetEntity();
        existingAsset.setId(assetId);
        existingAsset.setName("OldName");
        existingAsset.setPrice(new BigDecimal("1000.00"));

        when(assetRepository.findById(assetId)).thenReturn(Optional.of(existingAsset));

        // Act
        BaseResponse response = assetService.updateAsset(assetId, createAssetDto);

        // Assert
        verify(assetRepository, times(1)).save(existingAsset);
        verify(assetRepository, times(1)).delete(existingAsset);

        assertEquals("Silver", existingAsset.getName());
        assertEquals(new BigDecimal("2000.00"), existingAsset.getPrice());

        assertEquals(200, response.getCode());
        assertEquals("success", response.getStatus());
        assertEquals("Asset deleted successfully", response.getMessage());
    }

}