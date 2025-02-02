package com.example.demo.controller;

import com.example.demo.security.JwtUtil;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.dto.LoginRequest;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import com.example.demo.dto.UserRegistrationRequest;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        try {
            System.out.println("Received registration request for user: " + user.getUsername()); // Debug log
            User createdUser = userService.createUser(user);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("username", createdUser.getUsername());
            response.put("email", createdUser.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace(); // This will print the full stack trace
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            errorResponse.put("errorType", e.getClass().getSimpleName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        boolean authenticated = userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
        if (authenticated) {
            String token = jwtUtil.generateToken(loginRequest.getEmail());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/profile")
    public String getProfile(Authentication authentication) {
        if (authentication == null) {
            throw new RuntimeException("Authentication object is null");
        }
        return "Welcome, " + authentication.getName();
    }
}