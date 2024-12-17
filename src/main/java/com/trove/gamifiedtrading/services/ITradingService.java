package com.trove.gamifiedtrading.services;

import com.trove.gamifiedtrading.data.body.BaseResponse;
import com.trove.gamifiedtrading.data.dto.BuyAssetDto;
import com.trove.gamifiedtrading.data.dto.CreateAssetDto;

public interface ITradingService {
        BaseResponse createAsset(CreateAssetDto createAssetDto);
        BaseResponse buyAsset(BuyAssetDto buyAssetDto);
        BaseResponse sellAsset(BuyAssetDto buyAssetDto);
}
