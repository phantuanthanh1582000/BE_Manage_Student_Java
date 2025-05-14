package com.example.studentmanagement.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "departments")
public class Department {

    @Id
    private String id;

    private String name;
    private String departmentCode;

    private List<String> majorIds = new ArrayList<>(); // chứa ID các Major

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    private boolean isDeleted = false;

    public Department() {}

    public Department(String name, String departmentCode, List<String> majorIds) {
        this.name = name;
        this.departmentCode = departmentCode;
        this.majorIds = majorIds != null ? majorIds : new ArrayList<>();
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.isDeleted = false;
    }

    // Getters và Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartmentCode() { return departmentCode; }
    public void setDepartmentCode(String departmentCode) { this.departmentCode = departmentCode; }

    public List<String> getMajorIds() { return majorIds; }
    public void setMajorIds(List<String> majorIds) {
        this.majorIds = majorIds != null ? majorIds : new ArrayList<>();
    }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    public boolean isDeleted() { return isDeleted; }
    public void setDeleted(boolean deleted) { isDeleted = deleted; }
}
