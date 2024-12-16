package com.trove.gamifiedtrading.repository;

import com.trove.gamifiedtrading.entity.UserEntity;
import com.trove.gamifiedtrading.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public Optional<UserEntity> findByUsername(String username);
}

