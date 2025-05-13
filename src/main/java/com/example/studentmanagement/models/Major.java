package com.example.studentmanagement.models;

public class Major {

    private String name;
    private String majorCode;

    // Constructors
    public Major() {}

    public Major(String name, String majorCode) {
        this.name = name;
        this.majorCode = majorCode;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMajorCode() { return majorCode; }
    public void setMajorCode(String majorCode) { this.majorCode = majorCode; }
}
