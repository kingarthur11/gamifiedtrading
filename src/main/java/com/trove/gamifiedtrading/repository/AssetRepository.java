package com.trove.gamifiedtrading.repository;

import com.trove.gamifiedtrading.entity.AssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<AssetEntity, Long> {
}

