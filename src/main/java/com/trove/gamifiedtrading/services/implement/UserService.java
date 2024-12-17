package com.trove.gamifiedtrading.services.implement;

import com.trove.gamifiedtrading.data.body.ApiResponse;
import com.trove.gamifiedtrading.data.body.BaseResponse;
import com.trove.gamifiedtrading.data.dto.CreateUserDto;
import com.trove.gamifiedtrading.entity.UserEntity;
import com.trove.gamifiedtrading.entity.WalletEntity;
import com.trove.gamifiedtrading.repository.UserRepository;
import com.trove.gamifiedtrading.repository.WalletRepository;
import com.trove.gamifiedtrading.services.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;


    public UserService(UserRepository userRepository,
                       WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    public ApiResponse<List<UserEntity>> getAllUsers() {
        var response = new ApiResponse<List<UserEntity>>();
        List<UserEntity> users = userRepository.findAll();

        response.setStatus("success");
        response.setMessage("Users retrieved successfully.");
        response.setCode(200);
        response.setResult(users);

        return response;
    };

    @Override
    public ApiResponse<Optional<UserEntity>> getUserById(Long id){
        var response = new ApiResponse<Optional<UserEntity>>();
        Optional<UserEntity> userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {
            response.setStatus("success");
            response.setMessage("User retrieved successfully.");
            response.setCode(200);
            response.setResult(userEntity);
        } else {
            response.setStatus("error");
            response.setMessage("User not found.");
            response.setCode(404);
            response.setResult(Optional.empty());
        }

        return response;
    };

    @Override
    public BaseResponse saveUser(CreateUserDto createUserDto) {
        var response = new BaseResponse();

        Optional<UserEntity> userEntityOpt = userRepository.findByUsername(createUserDto.username());

        if (userEntityOpt.isPresent()) {
            response.setCode(400);
            response.setStatus("error");
            response.setMessage("User already exist.");
            return response;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(createUserDto.username());
        UserEntity userEnt = userRepository.save(userEntity);

        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setUserId(userEnt.getId());
        walletRepository.save(walletEntity);

        response.setCode(200);
        response.setStatus("success");
        response.setMessage("User created successfully.");

        return response;
    };

}

