package com.trove.gamifiedtrading.services.implement;

import com.trove.gamifiedtrading.data.body.BaseResponse;
import com.trove.gamifiedtrading.data.dto.CreatePortforlioDto;
import com.trove.gamifiedtrading.entity.PortfolioEntity;
import com.trove.gamifiedtrading.entity.AssetEntity;
import com.trove.gamifiedtrading.repository.PortfolioRepository;
import com.trove.gamifiedtrading.repository.AssetRepository;
import com.trove.gamifiedtrading.services.IPortfolioService;

import java.util.Optional;

public class PortfolioService implements IPortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final AssetRepository assetRepository;

    public PortfolioService(PortfolioRepository portfolioRepository,
                            AssetRepository assetRepository){
        this.portfolioRepository = portfolioRepository;
        this.assetRepository = assetRepository;
    }

    @Override
    public BaseResponse addAsset(CreatePortforlioDto createPortforlioDto) {
        var response = new BaseResponse();

        Optional<PortfolioEntity> existingPortfolioOpt = portfolioRepository.findByUserId(createPortforlioDto.userId())
            .filter(portf -> portf.getAssetId().equals(createPortforlioDto.assetId()));

        if (existingPortfolioOpt.isPresent()) {
            response.setCode(400);
            response.setStatus("error");
            response.setMessage("Portfolio already exists for the given asset.");
            return response;
        }

        Optional<AssetEntity> assetEntityOpt = assetRepository.findById(createPortforlioDto.assetId());

        if (assetEntityOpt.isEmpty()) {
            response.setCode(404);
            response.setStatus("error");
            response.setMessage("Asset not found.");
            return response;
        }

        AssetEntity assetEntity = assetEntityOpt.get();

        PortfolioEntity newPortfolio = new PortfolioEntity();
        newPortfolio.setUserId(createPortforlioDto.userId());
        newPortfolio.setAssetId(createPortforlioDto.assetId());
        newPortfolio.setName(assetEntity.getName());

        portfolioRepository.save(newPortfolio);

        response.setCode(200);
        response.setStatus("success");
        response.setMessage("Asset added to portfolio successfully.");

        return response;

    }

    @Override
    public BaseResponse removeAsset(CreatePortforlioDto createPortforlioDto) {
        var response = new BaseResponse();

        Optional<PortfolioEntity> existingPortfolioOpt = portfolioRepository.findByUserId(createPortforlioDto.userId())
            .filter(portf -> portf.getAssetId().equals(createPortforlioDto.assetId()));

        if (existingPortfolioOpt.isEmpty()) {
            response.setCode(400);
            response.setStatus("error");
            response.setMessage("Portfolio does not exists for the given asset.");
            return response;
        }

        Optional<AssetEntity> assetEntityOpt = assetRepository.findById(createPortforlioDto.assetId());

        if (assetEntityOpt.isEmpty()) {
            response.setCode(404);
            response.setStatus("error");
            response.setMessage("Asset not found.");
            return response;
        }

        PortfolioEntity portfolio = existingPortfolioOpt.get();
        portfolioRepository.deleteById(portfolio.getId());

        response.setCode(200);
        response.setStatus("success");
        response.setMessage("Asset removed successfully");

        return response;
    }

    @Override
    public PortfolioEntity calculateAssetValue(int quantity) {
        return null;
    }
}
