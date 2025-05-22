package com.example.studentmanagement.services;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.studentmanagement.models.AttendanceModel;
import com.example.studentmanagement.models.LessonModel;
import com.example.studentmanagement.models.Schedule;
import com.example.studentmanagement.models.User;
import com.example.studentmanagement.repositories.AttendanceRepository;
import com.example.studentmanagement.repositories.LessonRepository;
import com.example.studentmanagement.repositories.ScheduleRepository;
import com.example.studentmanagement.repositories.UserRepository;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LessonRepository lessonRepository;

    public List<AttendanceModel> createBulkAttendanceByScheduleId(String scheduleId) {
        // 1. Lấy tất cả lesson theo scheduleId
        List<LessonModel> lessons = lessonRepository.findByScheduleId(scheduleId);
        if (lessons.isEmpty()) {
            throw new RuntimeException("Không có lesson nào trong schedule này");
        }

        List<AttendanceModel> allAttendances = new ArrayList<>();

        for (LessonModel lesson : lessons) {
            // Kiểm tra lesson đã có điểm danh chưa
            if (attendanceRepository.existsByLessonId(lesson.getId()))
                continue;

            // Lấy học sinh trong lớp tương ứng
            Schedule schedule = scheduleRepository.findById(scheduleId)
                    .orElseThrow(() -> new RuntimeException("Schedule không tồn tại"));

            List<User> students = userRepository.findByClassIdAndRole(schedule.getClassId(), "student");

            for (User student : students) {
                AttendanceModel att = new AttendanceModel();
                att.setLessonId(lesson.getId());
                att.setStudentId(student.getId());
                att.setDate(lesson.getDate());
                att.setStatus("Absent");
                allAttendances.add(att);
            }
        }

        return attendanceRepository.saveAll(allAttendances);
    }
    
    public List<AttendanceModel> getByLessonId(String lessonId) {
        return attendanceRepository.findByLessonId(lessonId);
    }
}
