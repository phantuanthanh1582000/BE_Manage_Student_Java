package com.example.studentmanagement.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.studentmanagement.models.LessonModel;

@Repository
public interface LessonRepository extends MongoRepository<LessonModel, String> {
    List<LessonModel> findByScheduleId(String scheduleId);
    Optional<LessonModel> findByNameAndScheduleId(String name, String scheduleId);
    Optional<LessonModel> findByIdAndScheduleId(String id, String scheduleId);

    List<LessonModel> findByDateAndScheduleId(LocalDate date, String scheduleId);
    List<LessonModel> findByDate(LocalDate date);

 
}
