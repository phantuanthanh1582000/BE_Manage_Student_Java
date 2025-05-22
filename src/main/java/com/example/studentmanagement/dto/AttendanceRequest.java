package com.example.studentmanagement.dto;

public class AttendanceRequest {
    private String lessonId;
    private String studentId;
    private String status;

    public AttendanceRequest() {
    }
    public AttendanceRequest(String lessonId, String studentId, String status) {
        this.lessonId = lessonId;
        this.studentId = studentId;
        this.status = status;
    }
    // ======= GETTERS & SETTERS =======
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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "AttendanceRequest{" +
                "lessonId='" + lessonId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
