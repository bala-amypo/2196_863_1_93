package com.example.demo.service;

import com.example.demo.model.User;

public interface UserService {
    User registerUser(String name, String email, String password, String role);
    User authenticateUser(String email, String password);
    User findByEmail(String email);
    User findById(Long id);
}