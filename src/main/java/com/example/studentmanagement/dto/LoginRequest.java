package com.example.studentmanagement.dto;

public class LoginRequest {
    private String email;
    private String password;

    // Constructor, getters và setters
    public LoginRequest() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
