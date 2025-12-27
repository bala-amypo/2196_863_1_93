package com.example.demo.service;

import com.example.demo.model.User;

public interface UserService {
    User register(User user);
    User authenticateUser(String email, String password);
    User findByEmail(String email);
    User findById(Long id);
}