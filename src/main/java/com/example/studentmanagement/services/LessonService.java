package com.example.studentmanagement.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.studentmanagement.models.LessonModel;
import com.example.studentmanagement.models.Schedule;
import com.example.studentmanagement.models.Subject;
import com.example.studentmanagement.repositories.LessonRepository;
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
}
