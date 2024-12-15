package com.trove.gamifiedtrading.services;

import com.trove.gamifiedtrading.data.body.BaseResponse;
import com.trove.gamifiedtrading.data.dto.CreatePortforlioDto;
import com.trove.gamifiedtrading.entity.PortfolioEntity;

public interface IPortfolioService {
        BaseResponse addAsset(CreatePortforlioDto createPortforlioDto);
        BaseResponse removeAsset(CreatePortforlioDto createPortforlioDto);
        PortfolioEntity calculateAssetValue(int  quantity);
}
