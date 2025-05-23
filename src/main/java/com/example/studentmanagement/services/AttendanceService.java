package com.example.studentmanagement.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.studentmanagement.dto.AttendanceDTO;
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

    @Autowired
    private UserRepository studentRepository;

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

    // public List<AttendanceModel> getByLessonId(String lessonId) {
    // return attendanceRepository.findByLessonId(lessonId);
    // }

    public List<AttendanceDTO> getAttendancesWithStudentName(String lessonId) {
        List<AttendanceModel> attendances = attendanceRepository.findByLessonId(lessonId);
        List<AttendanceDTO> result = new ArrayList<>();

        for (AttendanceModel att : attendances) {
            AttendanceDTO dto = new AttendanceDTO();
            dto.setId(att.getId());
            dto.setLessonId(att.getLessonId());
            dto.setStudentId(att.getStudentId());
            dto.setStatus(att.getStatus());
            dto.setDate(att.getDate());

            // Truy student theo studentId
            User student = studentRepository.findById(att.getStudentId()).orElse(null);
            if (student != null) {
                dto.setStudentName(student.getFullName());
            } else {
                dto.setStudentName("N/A");
            }

            result.add(dto);
        }

        return result;
    }

    public void updateAttendanceStatuses(List<String> ids, String newStatus) {
        List<AttendanceModel> attendances = attendanceRepository.findByIdIn(ids);
        if (attendances.isEmpty()) {
            throw new RuntimeException("Không tìm thấy điểm danh nào với các ID đã cho");
        }
        for (AttendanceModel att : attendances) {
            att.setStatus(newStatus);
            att.setUpdatedAt(LocalDateTime.now()); // nếu có
        }

        attendanceRepository.saveAll(attendances);
    }
}
