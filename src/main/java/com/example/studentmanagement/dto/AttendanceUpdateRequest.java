package com.example.studentmanagement.dto;

import java.util.List;

public class AttendanceUpdateRequest {
       private List<String> ids;
    private String status;


    public List<String> getIds() {
        return ids;
    }
    public void setIds(List<String> ids) {
        this.ids = ids;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
}
