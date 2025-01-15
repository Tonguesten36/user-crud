package com.toshiba.intern.usercrud.controller;

import com.toshiba.intern.usercrud.entity.User;
import com.toshiba.intern.usercrud.payloads.dtos.UpdateUserDto;
import com.toshiba.intern.usercrud.payloads.dtos.UserCreateDto;
import com.toshiba.intern.usercrud.service.user.UserServiceImpl;
import io.github.cdimascio.dotenv.Dotenv;
import io.sentry.Sentry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> getAllUsers() {
        try{
            List<User> users = userService.getAllUsers();
            System.out.println(users);
            return users;
        }
        catch(Exception e){
            Sentry.captureException(e);
            System.out.println("Sentry captured an exception");
        }
        return null;
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(value = HttpStatus.CREATED)
    public User createUser(@RequestBody UserCreateDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public ResponseEntity<User> getUserById(@PathVariable int userId) {
        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable int userId, @RequestBody UpdateUserDto newUser)
    {
        Dotenv dotenv = Dotenv.load();
        int admin_id = Integer.parseInt(dotenv.get("ADMIN_ID"));
        if(userId == admin_id){
            return new ResponseEntity<>("Cannot update admin.", HttpStatus.BAD_REQUEST);
        }
        userService.updateUser(userId, newUser);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {

        Dotenv dotenv = Dotenv.load();
        int admin_id = Integer.parseInt(dotenv.get("ADMIN_ID"));
        if(userId == admin_id){
            return new ResponseEntity<String>("Cannot delete admin.", HttpStatus.BAD_REQUEST);
        }
        userService.deleteUser(userId);
        return new ResponseEntity<String>("User deleted successfully!.", HttpStatus.OK);
    }

}
