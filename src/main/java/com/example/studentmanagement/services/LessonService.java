package com.example.studentmanagement.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.studentmanagement.dto.LessonDetailDTO;
import com.example.studentmanagement.models.ClassModel;
import com.example.studentmanagement.models.LessonModel;
import com.example.studentmanagement.models.RoomModel;
import com.example.studentmanagement.models.Schedule;
import com.example.studentmanagement.models.Subject;
import com.example.studentmanagement.repositories.ClassRepository;
import com.example.studentmanagement.repositories.LessonRepository;
import com.example.studentmanagement.repositories.RoomRepository;
import com.example.studentmanagement.repositories.ScheduleRepository;
import com.example.studentmanagement.repositories.SubjectRepository;

@Service
public class LessonService {
    
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private RoomRepository roomRepository;

    public List<LessonModel> generateLessons(String scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new RuntimeException("Schedule not found"));
        Subject subject = subjectRepository.findById(schedule.getSubjectId())
            .orElseThrow(() -> new RuntimeException("Subject not found"));

        // Định dạng ngày tháng trong Schedule (ví dụ "2025-05-23")
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(schedule.getStartDate(), dateFormatter);
        LocalDate end = LocalDate.parse(schedule.getEndDate(), dateFormatter);

        // Không cần parse startTime và endTime thành LocalTime nữa
        String startTime = schedule.getStartTime(); // giữ nguyên String
        String endTime = schedule.getEndTime();

        DayOfWeek targetDay = DayOfWeek.valueOf(schedule.getDayOfWeek().toUpperCase()); // VD: TUESDAY
        String subjectName = subject.getName();

        List<LessonModel> lessons = new ArrayList<>();
        int lessonIndex = 1;

        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            if (date.getDayOfWeek().equals(targetDay)) {
                LessonModel lesson = new LessonModel();
                lesson.setName("Lesson " + lessonIndex + " : " + subjectName);
                lesson.setScheduleId(scheduleId);
                lesson.setDate(date.toString());
                lesson.setStartTime(startTime);
                lesson.setEndTime(endTime);
                lessons.add(lesson);
                lessonIndex++;
            }
        }

        return lessonRepository.saveAll(lessons);
    }

    public List<LessonModel> getLessonsByScheduleId(String scheduleId) {
        return lessonRepository.findByScheduleId(scheduleId);
    }

    public List<LessonModel> getLessonsByScheduleIdAndDate( String date) {
        return lessonRepository.findByDate(date);
    }

    // public List<LessonModel> getLessonsByTeacherAndDate(String teacherId, String dateStr) {
    //     List<Schedule> schedules = scheduleRepository.findByTeacherId(teacherId);
    //     List<String> scheduleIds = schedules.stream()
    //             .map(Schedule::getId)
    //             .collect(Collectors.toList());

    //     if (scheduleIds.isEmpty()) {
    //         System.out.println("Không có schedule nào cho giáo viên: " + teacherId);
    //         return new ArrayList<>();
    //     }

    //     System.out.println("Tìm lessons theo teacher: " + teacherId + " và ngày: " + dateStr);

    //     return lessonRepository.findByScheduleIdInAndDate(scheduleIds, dateStr);
    // }

    public List<LessonDetailDTO> getLessonsWithScheduleAndClass(String teacherId, String date) {
        List<Schedule> schedules = scheduleRepository.findByTeacherId(teacherId);
        List<String> scheduleIds = schedules.stream()
                .map(Schedule::getId)
                .collect(Collectors.toList());

        List<LessonModel> lessons = lessonRepository.findByScheduleIdInAndDate(scheduleIds, date);

        List<LessonDetailDTO> result = new ArrayList<>();

        for (LessonModel lesson : lessons) {
            LessonDetailDTO dto = new LessonDetailDTO();

            dto.setLessonId(lesson.getId());
            dto.setLessonName(lesson.getName());
            dto.setDate(lesson.getDate());
            dto.setStartTime(lesson.getStartTime());
            dto.setEndTime(lesson.getEndTime());

            Schedule schedule = scheduleRepository.findById(lesson.getScheduleId()).orElse(null);
            if (schedule != null) {
                dto.setScheduleId(schedule.getId());
                dto.setClassId(schedule.getClassId());

                // Tìm thông tin lớp nếu cần
                ClassModel classModel = classRepository.findById(schedule.getClassId()).orElse(null);
                if (classModel != null) {
                    dto.setClassName(classModel.getClassName());
                }
                RoomModel room = roomRepository.findById(schedule.getRoomId()).orElse(null);
                if (room != null) {
                    dto.setRoomName(room.getName());
                }
            }

            result.add(dto);
        }

        return result;
    }
}
