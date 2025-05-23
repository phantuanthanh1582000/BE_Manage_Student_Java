package com.example.studentmanagement.models;

public class JwtUser {
    private String id;
    private String email;
    private String role;

    public JwtUser(String id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
