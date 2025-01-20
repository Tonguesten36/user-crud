package com.toshiba.intern.usercrud.controller;

import com.toshiba.intern.usercrud.entity.Role;
import com.toshiba.intern.usercrud.payloads.dtos.LoginDto;
import com.toshiba.intern.usercrud.payloads.dtos.RegisterDto;
import com.toshiba.intern.usercrud.entity.User;
import com.toshiba.intern.usercrud.repository.RoleRepository;
import com.toshiba.intern.usercrud.repository.UserRepository;
import com.toshiba.intern.usercrud.service.security.UserDetailsImpl;
import com.toshiba.intern.usercrud.utils.JwtUtils;

import io.sentry.Sentry;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.toshiba.intern.usercrud.enums.ERole.ROLE_ADMIN;
import static com.toshiba.intern.usercrud.enums.ERole.ROLE_USER;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController
{
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final RoleRepository roleRepository;

    @PostMapping("/login")
    @Operation(summary = "logging in an existing account")
    @ApiResponses(value ={
        @ApiResponse(responseCode = "200", description = "JWT for {name of the user logged in}: {their JWT}", content = @Content
            ),
        @ApiResponse(responseCode="500", description = "Internal Server Error", content = @Content)
    })
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto)
    {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken
                            (loginDto.getIdentifier(), loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            return new ResponseEntity<>("JWT for " + userDetails.getUsername() + ": " + jwt, HttpStatus.OK);
        }
        catch (Exception e){
            Sentry.captureException(e);
        }

        return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/register")
    @Operation(summary = "registering a new account (without directly using the CRUD API)")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "201", description = "Username created successfully", content = @Content),
            @ApiResponse(responseCode = "500", description = "Failed to register user", content = @Content),
            @ApiResponse(responseCode = "400", description = "Username (or email) already exists", content = @Content),
    })
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto){
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }

        try{
            // Create new user's account
            User user = new User();
            user.setUsername(registerDto.getUsername());

            // Hash the password using bcrypt
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hashedPassword = encoder.encode(registerDto.getPassword());
            user.setPassword(hashedPassword);
            user.setEmail(registerDto.getEmail());

            // Assign role "ROLE_USER" to user
            Role role = roleRepository.findByName(ROLE_USER);
            user.setRoles(List.of(role));

            userRepository.save(user);

            return new ResponseEntity<>("Username created successfully", HttpStatus.CREATED);
        }
        catch (Exception e){
            Sentry.captureException(e);
        }
        return new ResponseEntity<>("Failed to register user", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(){
        if (userRepository.existsByUsername("admin")) {
            return new ResponseEntity<>("Admin already exists", HttpStatus.BAD_REQUEST);
        }

        try{
            // Create admin account
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            // Hash the password using bcrypt
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hashedPassword = encoder.encode("adminpass");
            admin.setPassword(hashedPassword);

            // Assign role "ROLE_ADMIN" to admin
            Role role = roleRepository.findByName(ROLE_ADMIN);
            admin.setRoles(List.of(role));

            userRepository.save(admin);

            return new ResponseEntity<>("Admin created successfully", HttpStatus.CREATED);
        }
        catch (Exception e){
            Sentry.captureException(e);
        }
        return new ResponseEntity<>("Failed to register admin", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
