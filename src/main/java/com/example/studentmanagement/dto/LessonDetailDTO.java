package com.example.studentmanagement.dto;

public class LessonDetailDTO  {
    private String lessonId;
    private String lessonName;
    private String date;
    private String startTime;
    private String endTime;

    private String scheduleId;
    private String roomName;
    private String classId;
    private String className;

 public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    public String getLessonId() {
        return lessonId;
    }
    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }
    public String getLessonName() {
        return lessonName;
    }
    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
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
    public String getScheduleId() {
        return scheduleId;
    }
    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }
    public String getClassId() {
        return classId;
    }
    public void setClassId(String classId) {
        this.classId = classId;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    @Override
    public String toString() {
        return "LessonDetailDTO [lessonId=" + lessonId + ", lessonName=" + lessonName + ", date=" + date
                + ", startTime=" + startTime + ", endTime=" + endTime + ", scheduleId=" + scheduleId
                + ", scheduleName=" + ", classId=" + classId + ", className="
                + className + "]";
    }
}
