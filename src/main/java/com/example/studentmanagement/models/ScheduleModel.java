package com.example.studentmanagement.models;

import org.springframework.data.annotation.Id;

public class ScheduleModel {
    @Id
    private String id;

    private String classId;
    private String subjectId;
    private String teacherId;
    private String startTime;
    private String endTime;
    private String startDate;
    private String endDate;
    private String dayOfWeek;
    private String roomId;

    public ScheduleModel() {
    }

    public ScheduleModel(String id, String classId, String subjectId, String teacherId, String startTime,
            String endTime, String startDate, String endDate, String dayOfWeek, String roomId) {
        this.id = id;
        this.classId = classId;
        this.subjectId = subjectId;
        this.teacherId = teacherId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dayOfWeek = dayOfWeek;
        this.roomId = roomId;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getClassId() {
        return classId;
    }
    public void setClassId(String classId) {
        this.classId = classId;
    }
    public String getSubjectId() {
        return subjectId;
    }
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
    public String getTeacherId() {
        return teacherId;
    }
    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String getDayOfWeek() {
        return dayOfWeek;
    }
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    public String getRoomId() {
        return roomId;
    }
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    @Override
    public String toString() {
        return "ScheduleModel [id=" + id + ", classId=" + classId + ", subjectId=" + subjectId + ", teacherId="
                + teacherId + ", startTime=" + startTime + ", endTime=" + endTime + ", startDate=" + startDate
                + ", endDate=" + endDate + ", dayOfWeek=" + dayOfWeek + ", roomId=" + roomId + "]";
    }
}
