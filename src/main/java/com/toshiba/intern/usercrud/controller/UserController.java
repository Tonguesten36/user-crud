package com.toshiba.intern.usercrud.controller;

import com.toshiba.intern.usercrud.entity.User;
import com.toshiba.intern.usercrud.payloads.dtos.UpdateUserDto;
import com.toshiba.intern.usercrud.payloads.dtos.UserCreateDto;
import com.toshiba.intern.usercrud.service.user.UserServiceImpl;
import io.github.cdimascio.dotenv.Dotenv;
import io.sentry.Sentry;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    @Operation(summary = "Read all users")
    @ApiResponses(value ={
        @ApiResponse(
            responseCode = "200",
            description = "Get all users in the database",
            content = @Content())
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> getAllUsers() {
        try{
            return userService.getAllUsers();
        }
        catch(Exception e){
            Sentry.captureException(e);
        }
        return null;
    }

    @PostMapping("/")
    @Operation(summary = "Create a new user")
    @ApiResponses(value ={
        @ApiResponse(
            responseCode = "201",
            description = "Create a new user using the CRUD API directly, require admin JWT",
            content = @Content()
        )
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(value = HttpStatus.CREATED)
    public User createUser(@RequestBody UserCreateDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Find an user by their ID")
    @ApiResponses(value ={
        @ApiResponse(
            responseCode = "200",
            content = @Content()
        ),
        @ApiResponse(
            responseCode = "404",
            content = @Content()
        )
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<User> getUserById(@PathVariable int userId) {
        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{userId}")
    @Operation(summary = "Update a specific user by their ID")
    @ApiResponses(value ={
        @ApiResponse(
            responseCode = "200",
                content = @Content()
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Cannot update admin",
                content = @Content()
        )
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable int userId, @RequestBody UpdateUserDto newUser)
    {
        if(userId == getAdminId()){
            return new ResponseEntity<>("Cannot update admin.", HttpStatus.BAD_REQUEST);
        }
        userService.updateUser(userId, newUser);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{userId}")
    @Operation(summary = "Delete a specific user by their ID")
    @ApiResponses(value ={
        @ApiResponse(
                responseCode = "200",
                description = "User deleted successfully!",
                content = @Content()
        ),
        @ApiResponse(
                responseCode = "400",
                description = "Cannot delete admin.",
                content = @Content()
        )
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        if(userId == getAdminId()){
            return new ResponseEntity<>("Cannot delete admin.", HttpStatus.BAD_REQUEST);
        }
        userService.deleteUser(userId);
        return new ResponseEntity<>("User deleted successfully!.", HttpStatus.OK);
    }

    public int getAdminId(){
        Dotenv env = Dotenv.configure().load();
        return Integer.parseInt(env.get("ADMIN_ID"));
    }
}
