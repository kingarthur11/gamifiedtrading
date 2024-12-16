package com.trove.gamifiedtrading.services.implement;

import com.trove.gamifiedtrading.data.body.ApiResponse;
import com.trove.gamifiedtrading.data.body.BaseResponse;
import com.trove.gamifiedtrading.data.dto.CreateUserDto;
import com.trove.gamifiedtrading.entity.UserEntity;
import com.trove.gamifiedtrading.entity.WalletEntity;
import com.trove.gamifiedtrading.repository.UserRepository;
import com.trove.gamifiedtrading.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        // Arrange
        UserEntity user1 = new UserEntity(1L, "john", 10, 2, 0, 10, 3);
        UserEntity user2 = new UserEntity(2L, "doe", 10, 2, 0, 10, 3);
        List<UserEntity> mockUsers = List.of(user1, user2);

        when(userRepository.findAll()).thenReturn(mockUsers);

        // Act
        ApiResponse<List<UserEntity>> response = userService.getAllUsers();

        // Assert
        assertEquals(200, response.getCode());
        assertEquals("success", response.getStatus());
        assertEquals("Users retrieved successfully.", response.getMessage());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() {
        // Arrange
        UserEntity user = new UserEntity(1L, "john", 10, 2, 0, 10, 3);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        ApiResponse<Optional<UserEntity>> response = userService.getUserById(1L);

        // Assert
        assertEquals(200, response.getCode());
        assertEquals("success", response.getStatus());
        assertEquals("User retrieved successfully.", response.getMessage());
        assertTrue(response.getResult().isPresent());
        assertEquals(user, response.getResult().get());

        verify(userRepository, times(1)).findById(1L);
    }


    @Test
    void getUserById_ShouldReturnEmpty_WhenUserDoesNotExist() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        ApiResponse<Optional<UserEntity>> response = userService.getUserById(1L);

        // Assert
//        assertTrue(result.isEmpty());
        assertEquals(404, response.getCode());
        assertEquals("error", response.getStatus());
        assertEquals("User not found.", response.getMessage());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void saveUser_ShouldCreateUserAndWallet() {
        // Arrange
        CreateUserDto createUserDto = new CreateUserDto("john");

        UserEntity savedUserEntity = new UserEntity();
        savedUserEntity.setId(1L);
        savedUserEntity.setUsername(createUserDto.username());

        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setUserId(savedUserEntity.getId());

        when(userRepository.findByUsername(createUserDto.username())).thenReturn(Optional.empty());
        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUserEntity);
        when(walletRepository.save(any(WalletEntity.class))).thenReturn(walletEntity);

        // Act
        BaseResponse response = userService.saveUser(createUserDto);

        // Assert
        assertEquals(200, response.getCode());
        assertEquals("success", response.getStatus());
        assertEquals("User created successfully.", response.getMessage());

        verify(userRepository, times(1)).findByUsername(createUserDto.username());
        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(walletRepository, times(1)).save(any(WalletEntity.class));
    }

    @Test
    void saveUser_ShouldReturnError_WhenUserAlreadyExists() {
        // Arrange
        CreateUserDto createUserDto = new CreateUserDto("john");

        UserEntity existingUserEntity = new UserEntity();
        existingUserEntity.setId(1L);
        existingUserEntity.setUsername(createUserDto.username());

        // Mock userRepository to return existing user (user already exists)
        when(userRepository.findByUsername(createUserDto.username())).thenReturn(Optional.of(existingUserEntity));

        // Act
        BaseResponse response = userService.saveUser(createUserDto);

        // Assert
        assertEquals(400, response.getCode());
        assertEquals("error", response.getStatus());
        assertEquals("User already exist.", response.getMessage());

        verify(userRepository, times(1)).findByUsername(createUserDto.username());
        verify(userRepository, never()).save(any(UserEntity.class));
        verify(walletRepository, never()).save(any(WalletEntity.class));
    }

}