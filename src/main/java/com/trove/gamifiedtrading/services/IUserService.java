package com.trove.gamifiedtrading.services;

import com.trove.gamifiedtrading.data.body.ApiResponse;
import com.trove.gamifiedtrading.data.body.BaseResponse;
import com.trove.gamifiedtrading.data.dto.CreateUserDto;
import com.trove.gamifiedtrading.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    ApiResponse<List<UserEntity>> getAllUsers();
    ApiResponse<Optional<UserEntity>> getUserById(Long id);
    BaseResponse saveUser(CreateUserDto createUserDto);
}
