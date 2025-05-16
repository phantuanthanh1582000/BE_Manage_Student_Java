package com.example.studentmanagement.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    
    private String email;

    private String password;

    private String role;

    private boolean isDelete;

    private String classId;

    private String majorId;

    private String fullName;

    private String phoneNumber;

    private String address;

     @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    public User() {
        this.isDelete = false;
    }

    public User(String email, String password, String role, String classId, String majorId, String fullName,
            String phoneNumber, String address) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.classId = classId;
        this.majorId = majorId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isDelete = false;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

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
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public boolean isDelete() {
        return isDelete;
    }
    public void setDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }
    public String getClassId() {
        return classId;
    }
    public void setClassId(String classId) {
        this.classId = classId;
    }
    public String getMajorId() {
        return majorId;
    }
    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "UserModel [id=" + id + ", email=" + email + ", password=" + password + ", role=" + role + ", isDelete="
                + isDelete + ", classId=" + classId + ", majorId=" + majorId + ", fullName=" + fullName
                + ", phoneNumber=" + phoneNumber + ", address=" + address + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt + "]";
    }
}
