package com.example.studentmanagement.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

public class AttendanceModel {
      @Id
    private String id;

    private String lessonId;
    private String studentId;
    private String status;
    private String date;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    public AttendanceModel() {
    }

    public AttendanceModel(String lessonId, String studentId, String status) {
        this.lessonId = lessonId;
        this.studentId = studentId;
        this.status = status;
    }

    // ======= GETTERS & SETTERS =======
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getLessonId() {
        return lessonId;
    }
    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    @Override
    public String toString() {
        return "AttendanceModel{" +
                "id='" + id + '\'' +
                ", lessonId='" + lessonId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
