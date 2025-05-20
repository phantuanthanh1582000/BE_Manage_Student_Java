package com.example.studentmanagement.utils;

public enum statusEnum {
    ACTIVE("ACTIVE"),
    PENDING("PENDING");
    
    private String status;

    statusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
