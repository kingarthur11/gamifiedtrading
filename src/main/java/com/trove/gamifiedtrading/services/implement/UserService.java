package com.trove.gamifiedtrading.services.implement;

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
    public List<UserEntity> getAllUsers() {
        System.out.println("get all user");
        return userRepository.findAll().stream().toList();
    };

    @Override
    public Optional<UserEntity> getUserById(Long id){
        System.out.println("get one user");
        return userRepository.findById(id);
    };

    @Override
    public UserEntity saveUser(CreateUserDto createUserDto) {
        System.out.println("this user has been created");

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(createUserDto.username());
        userEntity.setGemCount(createUserDto.gemCount());
        userEntity.setRank(createUserDto.rank());
        UserEntity userEnt = userRepository.save(userEntity);

        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setUserId(userEnt.getId());
        walletRepository.save(walletEntity);

        return userEnt;
    };

}

