package com.trove.gamifiedtrading.services;

import com.trove.gamifiedtrading.entity.AssetEntity;

public interface IAssetService {
    AssetEntity createAsset(CreateAssetDto createAssetDto);
}
