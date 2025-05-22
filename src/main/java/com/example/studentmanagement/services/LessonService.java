package com.example.studentmanagement.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
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


        List<LessonModel> lessons = new ArrayList<>();
        LocalDate start = schedule.getStartDate();
        LocalDate end = schedule.getEndDate();
        DayOfWeek targetDay = DayOfWeek.valueOf(schedule.getDayOfWeek().toUpperCase()); // e.g. TUESDAY
        String subjectName = subject.getName();

        int lessonIndex = 1;
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            if (date.getDayOfWeek().equals(targetDay)) {
                LessonModel lesson = new LessonModel();
                lesson.setName("Lesson " + lessonIndex + " : " + subjectName);
                lesson.setScheduleId(scheduleId);
                lesson.setDate(date);
                lesson.setStartTime(schedule.getStartTime());
                lesson.setEndTime(schedule.getEndTime());
                lessons.add(lesson);
                lessonIndex++;
            }
        }

        return lessonRepository.saveAll(lessons);
    }

    public List<LessonModel> getLessonsByScheduleId(String scheduleId) {
        return lessonRepository.findByScheduleId(scheduleId);
    }
}
