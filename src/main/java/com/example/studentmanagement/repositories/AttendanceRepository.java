package com.example.studentmanagement.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.studentmanagement.models.AttendanceModel;

@Repository
public interface AttendanceRepository extends MongoRepository<AttendanceModel, String> {
   List<AttendanceModel> findByLessonIdIn(List<String> lessonIds);

   boolean existsByLessonId(String lessonId);

   List<AttendanceModel> findByLessonId(String id);

   @Query("{ '_id': { $in: ?0 } }")
   List<AttendanceModel> findByIdIn(List<String> ids);
}