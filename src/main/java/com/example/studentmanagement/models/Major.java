package com.example.studentmanagement.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "majors")
public class Major {

    @Id
    private String id;

    private List<String> classIds = new ArrayList<>();
    private String name;
    private String majorCode;

    private boolean isDelete;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Major() {
        this.isDelete = false;
    }

    public Major(String name, String majorCode) {
        this.name = name;
        this.majorCode = majorCode;
        this.isDelete = false;
    }


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMajorCode() { return majorCode; }
    public void setMajorCode(String majorCode) { this.majorCode = majorCode; }

    public List<String> getClassIds() { return classIds; }
    public void setClassIds(List<String> classIds) { this.classIds = classIds; }

    public boolean isDelete() { return isDelete; }
    public void setDelete(boolean delete) { isDelete = delete; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
