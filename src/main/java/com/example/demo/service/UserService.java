package com.example.demo.service;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;

public interface UserService {

    // NEW (for correct API)
    User register(RegisterRequest request);

    // OLD (to satisfy existing tests)
    User register(User user);

    User authenticateUser(String email, String password);

    User findByEmail(String email);
}
