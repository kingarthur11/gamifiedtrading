package com.trove.gamifiedtrading.services;

import com.trove.gamifiedtrading.data.body.BaseResponse;
import com.trove.gamifiedtrading.data.dto.CreateAssetDto;

public interface IAssetService {
    BaseResponse createAsset(CreateAssetDto createAssetDto);
    BaseResponse updateAsset(Long id, CreateAssetDto createAssetDto);
}
