package com.example.demo.controller;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.LoginRequest;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "User authentication endpoints")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // ================= REGISTER =================
    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest request,
            BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        User registeredUser = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates user and returns a token")
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
