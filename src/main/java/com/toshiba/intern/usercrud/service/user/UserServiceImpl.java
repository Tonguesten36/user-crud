package com.toshiba.intern.usercrud.service.user;

import com.toshiba.intern.usercrud.entity.User;
import com.toshiba.intern.usercrud.payloads.dtos.UpdateUserDto;
import com.toshiba.intern.usercrud.payloads.dtos.UserCreateDto;
import com.toshiba.intern.usercrud.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(UserCreateDto newUser) {
        User user = new User();

        user.setUsername(newUser.username);
        // Hash the password using bcrypt
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(newUser.password);
        user.setPassword(hashedPassword);
        user.setEmail(newUser.email);
        user.setRoles(newUser.roles);

        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(int id, UpdateUserDto updateUserDto) {
        User user = userRepository.findById(id).isPresent() ? userRepository.findById(id).get() : null;
        if(user == null){
            System.out.println("user not found");
            return null;
        }

        user.setUsername(updateUserDto.getNewUsername());
        user.setEmail(updateUserDto.getNewEmail());

        // Hash the password using bcrypt
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(updateUserDto.getNewPassword());
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(int id) {
        User user = userRepository.findById(id).isPresent() ? userRepository.findById(id).get() : null;
        if(user == null){
            System.out.println("user not found");
            return;
        }

        userRepository.delete(user);
    }
}
