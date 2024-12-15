package com.trove.gamifiedtrading.services;

import com.trove.gamifiedtrading.data.dto.CreateUserDto;
import com.trove.gamifiedtrading.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<UserEntity> getAllUsers();
    Optional<UserEntity> getUserById(Long id);
    UserEntity saveUser(CreateUserDto createUserDto);
}
