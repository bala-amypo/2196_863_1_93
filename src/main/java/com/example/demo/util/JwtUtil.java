package com.example.demo.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtUtil {

    // Simple in-memory token storage
    private final Map<String, TokenInfo> tokenStore = new HashMap<>();
    
    public String generateToken(String email, Long userId, String role) {
        String token = "simple-" + UUID.randomUUID().toString().substring(0, 8);
        tokenStore.put(token, new TokenInfo(email, userId, role));
        return token;
    }
    
    public String extractUsername(String token) {
        TokenInfo info = tokenStore.get(token);
        return info != null ? info.email : null;
    }
    
    public Long extractUserId(String token) {
        TokenInfo info = tokenStore.get(token);
        return info != null ? info.userId : null;
    }
    
    public String extractRole(String token) {
        TokenInfo info = tokenStore.get(token);
        return info != null ? info.role : null;
    }
    
    private static class TokenInfo {
        String email;
        Long userId;
        String role;
        
        TokenInfo(String email, Long userId, String role) {
            this.email = email;
            this.userId = userId;
            this.role = role;
        }
    }
}