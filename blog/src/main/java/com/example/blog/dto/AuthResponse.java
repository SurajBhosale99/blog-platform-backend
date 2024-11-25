package com.example.blog.dto;

public class AuthResponse {

    private String token;  // Field to store the JWT token

    // Constructor that accepts the token
    public AuthResponse(String token) {
        this.token = token;
    }

    // Getter for token
    public String getToken() {
        return token;
    }
}
