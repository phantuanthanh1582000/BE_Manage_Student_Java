package com.example.studentmanagement.models;

import org.springframework.data.annotation.Id;

import com.example.studentmanagement.utils.statusEnum;

public class RoomModel {
    @Id
    private String id;
    private String name;

    private String type;
    
    private String status = statusEnum.ACTIVE.getStatus();

    public RoomModel() {
    }
    public RoomModel(String id, String name, String type, String status) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "RoomModel [id=" + id + ", name=" + name + ", type=" + type + ", status=" + status + "]";
    }
}
