package com.example.studentmanagement.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findByIsDeleteFalse();
    }

    @Transactional
    public Schedule addSchedule(Schedule schedule) {
        // 1. Validate subjectId
        if (!subjectRepository.existsById(schedule.getSubjectId())) {
            throw new RuntimeException("Môn học không tồn tại với ID: " + schedule.getSubjectId());
        }

        // 2. Validate classId
        if (!classRepository.existsById(schedule.getClassId())) {
            throw new RuntimeException("Lớp học không tồn tại với ID: " + schedule.getClassId());
        }

        // 3. Validate teacher
        var userOpt = userRepository.findById(schedule.getTeacherId());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Giáo viên không tồn tại với ID: " + schedule.getTeacherId());
        }

        var user = userOpt.get();
        if (!"teacher".equalsIgnoreCase(user.getRole())) {
            throw new RuntimeException("Người dùng không phải là giáo viên");
        }

        // 4. Validate room
        Optional<RoomModel> roomOpt = roomRepository.findById(schedule.getRoomId());
        if (roomOpt.isEmpty()) {
            throw new RuntimeException("Phòng học không tồn tại với ID: " + schedule.getRoomId());
        }
        RoomModel room = roomOpt.get();

        // 5. Parse ngày giờ từ String sang LocalDate và LocalTime
        LocalDate startDate = LocalDate.parse(schedule.getStartDate(), dateFormatter);
        LocalDate endDate = LocalDate.parse(schedule.getEndDate(), dateFormatter);
        LocalTime startTime = LocalTime.parse(schedule.getStartTime(), timeFormatter);
        LocalTime endTime = LocalTime.parse(schedule.getEndTime(), timeFormatter);

        String dayOfWeek = schedule.getDayOfWeek().toUpperCase();

        // 6. Kiểm tra trùng lịch
        List<Schedule> schedules = scheduleRepository.findByRoomIdAndDayOfWeekAndIsDeleteFalse(
            schedule.getRoomId(), dayOfWeek
        );

        for (Schedule existing : schedules) {
            LocalDate existingStartDate = LocalDate.parse(existing.getStartDate(), dateFormatter);
            LocalDate existingEndDate = LocalDate.parse(existing.getEndDate(), dateFormatter);
            LocalTime existingStartTime = LocalTime.parse(existing.getStartTime(), timeFormatter);
            LocalTime existingEndTime = LocalTime.parse(existing.getEndTime(), timeFormatter);

            boolean dateOverlap = !endDate.isBefore(existingStartDate) && !startDate.isAfter(existingEndDate);
            boolean timeOverlap = !endTime.isBefore(existingStartTime) && !startTime.isAfter(existingEndTime);

            if (dateOverlap && timeOverlap) {
                throw new RuntimeException("Trùng thời gian với lịch học đã tồn tại trong cùng phòng");
            }
        }

        // 7. Lưu lịch và cập nhật trạng thái phòng nếu cần
        schedule.setDayOfWeek(dayOfWeek);  // chuẩn hóa
        Schedule savedSchedule = scheduleRepository.save(schedule);

        if (statusEnum.ACTIVE.getStatus().equalsIgnoreCase(room.getStatus())) {
            room.setStatus(statusEnum.PENDING.getStatus());
            roomRepository.save(room);
        }

        return savedSchedule;
    }
}
