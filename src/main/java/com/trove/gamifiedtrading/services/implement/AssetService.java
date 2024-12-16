package com.trove.gamifiedtrading.services.implement;

import com.trove.gamifiedtrading.data.body.BaseResponse;
import com.trove.gamifiedtrading.data.dto.CreateAssetDto;
import com.trove.gamifiedtrading.entity.AssetEntity;
import com.trove.gamifiedtrading.repository.AssetRepository;
import com.trove.gamifiedtrading.services.IAssetService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssetService implements IAssetService {

    private final AssetRepository assetRepository;

    AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }
    @Override
    public BaseResponse createAsset(CreateAssetDto createAssetDto) {
        var response = new BaseResponse();

        AssetEntity assetEntity = new AssetEntity();
        assetEntity.setName(createAssetDto.name());
        assetEntity.setPrice(createAssetDto.price());
        assetRepository.save(assetEntity);

        response.setCode(200);
        response.setStatus("success");
        response.setMessage("Asset credited successfully");
        return response;
    }

    @Override
    public BaseResponse updateAsset(Long id, CreateAssetDto createAssetDto) {
        var response = new BaseResponse();

        Optional<AssetEntity> assetEntityOpt = assetRepository.findById(id);
        AssetEntity assetEntity = assetEntityOpt.get();

        assetEntity.setName(createAssetDto.name());
        assetEntity.setPrice(createAssetDto.price());
        assetRepository.save(assetEntity);

        assetRepository.delete(assetEntity);

        response.setCode(200);
        response.setStatus("success");
        response.setMessage("Asset deleted successfully");
        return response;
    }
}
