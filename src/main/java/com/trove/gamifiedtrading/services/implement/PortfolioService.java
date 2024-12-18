package com.trove.gamifiedtrading.services.implement;

import com.trove.gamifiedtrading.data.body.ApiResponse;
import com.trove.gamifiedtrading.data.body.BaseResponse;
import com.trove.gamifiedtrading.data.dto.ConvertAssetValue;
import com.trove.gamifiedtrading.data.dto.CreatePortforlioDto;
import com.trove.gamifiedtrading.entity.PortfolioEntity;
import com.trove.gamifiedtrading.entity.AssetEntity;
import com.trove.gamifiedtrading.entity.UserEntity;
import com.trove.gamifiedtrading.repository.PortfolioRepository;
import com.trove.gamifiedtrading.repository.AssetRepository;
import com.trove.gamifiedtrading.repository.UserRepository;
import com.trove.gamifiedtrading.services.IPortfolioService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PortfolioService implements IPortfolioService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final AssetRepository assetRepository;

    public PortfolioService(UserRepository userRepository,
                            PortfolioRepository portfolioRepository,
                            AssetRepository assetRepository){
        this.portfolioRepository = portfolioRepository;
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ApiResponse<List<PortfolioEntity>> getAllPortfolios() {
        var response = new ApiResponse<List<PortfolioEntity>>();
        List<PortfolioEntity> portfolios = portfolioRepository.findAll();

        response.setStatus("success");
        response.setMessage("Portfolios retrieved successfully.");
        response.setCode(200);
        response.setResult(portfolios);

        return response;
    }

    @Override
    public ApiResponse<Optional<PortfolioEntity>> getPortfolioById(Long id) {
        var response = new ApiResponse<Optional<PortfolioEntity>>();
        Optional<PortfolioEntity> portfolio = portfolioRepository.findById(id);

        if (portfolio.isPresent()) {
            response.setStatus("success");
            response.setMessage("Portfolio retrieved successfully.");
            response.setCode(200);
            response.setResult(portfolio);
        } else {
            response.setStatus("error");
            response.setMessage("Portfolio not found.");
            response.setCode(404);
            response.setResult(Optional.empty());
        }

        return response;
    }

    @Override
    public BaseResponse addAsset(CreatePortforlioDto createPortforlioDto) {
        var response = new BaseResponse();

        Optional<AssetEntity> assetEntityOpt = assetRepository.findById(createPortforlioDto.assetId());

        if (assetEntityOpt.isEmpty()) {
            response.setCode(404);
            response.setStatus("error");
            response.setMessage("Asset not found.");
            return response;
        }

        Optional<PortfolioEntity> existingPortfolioOpt = portfolioRepository.findByUserId(createPortforlioDto.userId())
            .filter(portf -> portf.getAssetId().equals(createPortforlioDto.assetId()));

        if (existingPortfolioOpt.isPresent()) {
            response.setCode(400);
            response.setStatus("error");
            response.setMessage("Portfolio already exists for the given asset.");
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

        Optional<AssetEntity> assetEntityOpt = assetRepository.findById(createPortforlioDto.assetId());

        if (assetEntityOpt.isEmpty()) {
            response.setCode(404);
            response.setStatus("error");
            response.setMessage("Asset not found.");
            return response;
        }

        Optional<PortfolioEntity> existingPortfolioOpt = portfolioRepository.findByUserId(createPortforlioDto.userId())
            .filter(portf -> portf.getAssetId().equals(createPortforlioDto.assetId()));

        if (existingPortfolioOpt.isEmpty()) {
            response.setCode(404);
            response.setStatus("error");
            response.setMessage("Portfolio does not exists for the given asset.");
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
    public ApiResponse<List<ConvertAssetValue>> calculateAssetValue() {
        var response = new ApiResponse<List<ConvertAssetValue>>();
        List<ConvertAssetValue> assetValues = portfolioRepository.findAll()
                .stream().map(this::assetValue).toList();

        response.setStatus("success");
        response.setMessage("AssetValues retrieved successfully.");
        response.setCode(200);
        response.setResult(assetValues);

        return response;
    }

    private ConvertAssetValue assetValue(PortfolioEntity portfolioEntity) {
        Optional<AssetEntity> assetEntityOpt = assetRepository.findById(portfolioEntity.getAssetId());
        Optional<UserEntity> userEntityOpt = userRepository.findById(portfolioEntity.getUserId());

        AssetEntity assetEntity = assetEntityOpt.get();
        UserEntity user = userEntityOpt.get();
        BigDecimal value = assetEntity.getPrice().multiply(new BigDecimal(portfolioEntity.getQuantity()));
        return new ConvertAssetValue(
                user.getUsername(),
                portfolioEntity.getQuantity(),
                assetEntity.getName(),
                value,
                assetEntity.getPrice()
        );
    }
}
