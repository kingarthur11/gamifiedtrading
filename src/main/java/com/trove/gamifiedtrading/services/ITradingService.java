package com.trove.gamifiedtrading.services;

import com.trove.gamifiedtrading.data.body.BaseResponse;
import com.trove.gamifiedtrading.data.dto.BuyAssetDto;

public interface ITradingService {
        BaseResponse buyAsset(BuyAssetDto buyAssetDto);
        BaseResponse sellAsset(BuyAssetDto buyAssetDto);
}
