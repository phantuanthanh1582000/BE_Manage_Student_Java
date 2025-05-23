package com.example.studentmanagement.dto;

public class AttendanceDTO {
    private String id;
    private String lessonId;
    private String studentId;
    private String studentName;
    private String status;
    private String date;

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
    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

}
