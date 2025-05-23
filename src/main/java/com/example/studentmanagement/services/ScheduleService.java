package com.example.studentmanagement.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.studentmanagement.models.RoomModel;
import com.example.studentmanagement.models.Schedule;
import com.example.studentmanagement.repositories.ClassRepository;
import com.example.studentmanagement.repositories.RoomRepository;
import com.example.studentmanagement.repositories.ScheduleRepository;
import com.example.studentmanagement.repositories.SubjectRepository;
import com.example.studentmanagement.repositories.UserRepository;
import com.example.studentmanagement.utils.statusEnum;

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

    @Transactional
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

        // Kiểm tra role của user
        var user = userOpt.get();
        if (!"teacher".equalsIgnoreCase(user.getRole())) {
            throw new RuntimeException("Người dùng không phải là giáo viên");
        }

        // Lấy phòng học
        Optional<RoomModel> roomOpt = roomRepository.findById(schedule.getRoomId());
        if (roomOpt.isEmpty()) {
            throw new RuntimeException("Phòng học không tồn tại với ID: " + schedule.getRoomId());
        }
        RoomModel room = roomOpt.get();

        // Kiểm tra trạng thái phòng nếu đang PENDING thì không được tạo lịch
        if (statusEnum.PENDING.getStatus().equalsIgnoreCase(room.getStatus())) {
            throw new RuntimeException("Phòng học đang trong trạng thái PENDING, không thể tạo lịch mới");
        }

        // Lưu lịch học
        Schedule savedSchedule = scheduleRepository.save(schedule);

        // Cập nhật trạng thái phòng từ ACTIVE sang PENDING nếu đang ACTIVE
        if (statusEnum.ACTIVE.getStatus().equalsIgnoreCase(room.getStatus())) {
            room.setStatus(statusEnum.PENDING.getStatus());
            roomRepository.save(room);
        }

        return savedSchedule;
    }
}
