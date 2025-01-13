package com.toshiba.intern.usercrud.service.user;

import com.toshiba.intern.usercrud.entity.User;
import com.toshiba.intern.usercrud.payloads.dtos.UpdateUserDto;
import com.toshiba.intern.usercrud.payloads.dtos.UserCreateDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(UserCreateDto newUser);

    Optional<User> getUserById(int id);

    List<User> getAllUsers();

    User updateUser(int id, UpdateUserDto updateUserDto);

    void deleteUser(int id);
}

