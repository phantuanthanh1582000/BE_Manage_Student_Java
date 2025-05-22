package com.example.studentmanagement.repositories;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.studentmanagement.models.LessonModel;


public interface LessonRepository extends MongoRepository<LessonModel, String> {
    List<LessonModel> findByScheduleId(String scheduleId);
    Optional<LessonModel> findByNameAndScheduleId(String name, String scheduleId);
    Optional<LessonModel> findByIdAndScheduleId(String id, String scheduleId);
    List<LessonModel> findByDateAndScheduleId(LocalTime date, String scheduleId);
    
}
