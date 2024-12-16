package com.trove.gamifiedtrading.services.implement;

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
        List<UserEntity> users = userService.getAllUsers();

        // Assert
        assertEquals(2, users.size());
        assertEquals("john", users.get(0).getUsername());
        assertEquals("doe", users.get(1).getUsername());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_ShouldReturnUser_WhenUserExists() {
        // Arrange
        UserEntity user = new UserEntity(1L, "john", 10, 2, 0, 10, 3);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        Optional<UserEntity> result = userService.getUserById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("john", result.get().getUsername());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserById_ShouldReturnEmpty_WhenUserDoesNotExist() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<UserEntity> result = userService.getUserById(1L);

        // Assert
        assertTrue(result.isEmpty());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void saveUser_ShouldCreateUserAndWallet() {
        // Arrange
        CreateUserDto createUserDto = new CreateUserDto("john", 10, 3);

        UserEntity savedUserEntity = new UserEntity();
        savedUserEntity.setId(1L);
        savedUserEntity.setUsername(createUserDto.username());
        savedUserEntity.setGemCount(createUserDto.gemCount());
        savedUserEntity.setRank(createUserDto.rank());

        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setUserId(savedUserEntity.getId());

        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUserEntity);
        when(walletRepository.save(any(WalletEntity.class))).thenReturn(walletEntity);

        // Act
        UserEntity result = userService.saveUser(createUserDto);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals("john", result.getUsername());
        assertEquals(10, result.getGemCount());
        assertEquals(3, result.getRank());

        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(walletRepository, times(1)).save(any(WalletEntity.class));
    }
}