package com.example.studentmanagement.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.studentmanagement.models.Schedule;
import com.example.studentmanagement.repositories.ClassRepository;
import com.example.studentmanagement.repositories.ScheduleRepository;
import com.example.studentmanagement.repositories.SubjectRepository;
import com.example.studentmanagement.repositories.UserRepository;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private UserRepository userRepository;

    // Lấy tất cả lịch học chưa bị xóa
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findByIsDeleteFalse();
    }

    // Tạo mới lịch học (có kiểm tra tồn tại các ID)
    public Schedule addSchedule(Schedule schedule) {
        // Kiểm tra subjectId
        if (!subjectRepository.existsById(schedule.getSubjectId())) {
            throw new RuntimeException("Môn học không tồn tại với ID: " + schedule.getSubjectId());
        }

        // Kiểm tra classId
        if (!classRepository.existsById(schedule.getClassId())) {
            throw new RuntimeException("Lớp học không tồn tại với ID: " + schedule.getClassId());
        }

        // Kiểm tra teacherId
        if (!userRepository.existsById(schedule.getTeacherId())) {
            throw new RuntimeException("Giáo viên không tồn tại với ID: " + schedule.getTeacherId());
        }

        return scheduleRepository.save(schedule);
    }
}
