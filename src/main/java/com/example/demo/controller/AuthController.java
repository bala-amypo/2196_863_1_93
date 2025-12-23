package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
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

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account")
    public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult result) {

        // Handle validation errors
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        // Let service throw exceptions; handled globally
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates user and returns a token")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {

        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        User user = userService.authenticateUser(email, password);

        Map<String, Object> response = new HashMap<>();
        response.put("user", user);
        response.put("token", "simple-token-" + user.getId());

        return ResponseEntity.ok(response);
    }
}
