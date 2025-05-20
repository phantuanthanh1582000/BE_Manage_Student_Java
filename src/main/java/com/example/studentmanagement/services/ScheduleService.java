package com.example.studentmanagement.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.studentmanagement.models.Schedule;
import com.example.studentmanagement.repositories.ClassRepository;
import com.example.studentmanagement.repositories.ScheduleRepository;
import com.example.studentmanagement.repositories.SubjectRepository;
import com.example.studentmanagement.repositories.UserRepository;
import com.example.studentmanagement.repositories.RoomRepository;

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

    @Autowired
    private RoomRepository roomRepository;

    
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findByIsDeleteFalse();
    }

    
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
    var userOpt = userRepository.findById(schedule.getTeacherId());
    if (userOpt.isEmpty()) {
        throw new RuntimeException("Giáo viên không tồn tại với ID: " + schedule.getTeacherId());
    }

    // ✅ Kiểm tra role của user
    var user = userOpt.get();
    if (!"teacher".equalsIgnoreCase(user.getRole())) {
        throw new RuntimeException("Người dùng không phải là giáo viên");
    }

    // Kiểm tra roomId
    if (!roomRepository.existsById(schedule.getRoomId())) {
        throw new RuntimeException("Phòng học không tồn tại với ID: " + schedule.getRoomId());
    }

    // Lưu lịch học
    return scheduleRepository.save(schedule);
}

}
