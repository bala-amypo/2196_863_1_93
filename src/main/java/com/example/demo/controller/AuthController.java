package com.example.demo.controller;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.LoginRequest;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // ================= REGISTER =================
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest request) {

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole()); // defaults to USER

        User registeredUser = userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {

        User user = userService.authenticateUser(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("user", user);
        response.put("token", "simple-token-" + user.getId());

        return ResponseEntity.ok(response);
    }
}
