package com.trove.gamifiedtrading.repository;

import com.trove.gamifiedtrading.entity.AssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetRepository extends JpaRepository<AssetEntity, Long> {
    public Optional<AssetEntity> findByName(String name);
}

