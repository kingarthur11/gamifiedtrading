package com.trove.gamifiedtrading.controllers;

import com.trove.gamifiedtrading.data.dto.CreateUserDto;
import com.trove.gamifiedtrading.entity.UserEntity;
import com.trove.gamifiedtrading.services.IUserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService iUserService;

    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @GetMapping("")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> userEntities =  iUserService.getAllUsers();
        return new ResponseEntity<>(userEntities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserEntity>> getUser(@PathVariable("id") Long id) {
        Optional<UserEntity> userEntity = iUserService.getUserById(id);
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    @PostMapping("/")
    public String saveUser(@RequestBody CreateUserDto createUserDto) {
        iUserService.saveUser(createUserDto);
        return "this user has been updated";
    }

}
