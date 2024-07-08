package com.PathFinder.PathFinder.controllers;

import com.PathFinder.PathFinder.models.User;
import com.PathFinder.PathFinder.security.JwtTokenProvider;
import com.PathFinder.PathFinder.services.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.ExtendedBeanInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/all")
    public List<User> getAll(){
        return userService.getAllUsers();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user){
        try{
            if(!userService.loginUser(user)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Credentials");

            return ResponseEntity.ok(jwtTokenProvider.generateToken(user));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {

            if (!userService.registerUser(user)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
            }

            return ResponseEntity.ok(jwtTokenProvider.generateToken(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed");
        }
    }

}
