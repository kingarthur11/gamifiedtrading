package com.trove.gamifiedtrading.services;

import com.trove.gamifiedtrading.data.body.ApiResponse;
import com.trove.gamifiedtrading.data.body.BaseResponse;
import com.trove.gamifiedtrading.data.dto.CreatePortforlioDto;
import com.trove.gamifiedtrading.entity.PortfolioEntity;
import com.trove.gamifiedtrading.entity.WalletEntity;

import java.util.List;
import java.util.Optional;

public interface IPortfolioService {
        ApiResponse<List<PortfolioEntity>> getAllPortfolios();
        ApiResponse<Optional<PortfolioEntity>> getPortfolioById(Long id);
        BaseResponse addAsset(CreatePortforlioDto createPortforlioDto);
        BaseResponse removeAsset(CreatePortforlioDto createPortforlioDto);
        PortfolioEntity calculateAssetValue(int  quantity);
}
