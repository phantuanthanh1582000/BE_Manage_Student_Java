package com.example.studentmanagement.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.studentmanagement.models.Schedule;

public interface ScheduleRepository extends MongoRepository<Schedule, String> {
    
    // Lấy tất cả lịch học chưa bị xóa
    List<Schedule> findByIsDeleteFalse();

    // Lấy lịch học theo lớp
    List<Schedule> findByClassIdAndIsDeleteFalse(String classId);

    // Lấy lịch học theo giáo viên
    List<Schedule> findByTeacherIdAndIsDeleteFalse(String teacherId);

    // Lấy lịch học theo môn học
    List<Schedule> findBySubjectIdAndIsDeleteFalse(String subjectId);

    List<Schedule> findByRoomIdAndDayOfWeekAndIsDeleteFalse(String roomId, String dayOfWeek);
}
