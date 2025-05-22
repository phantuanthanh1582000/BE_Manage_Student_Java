package com.example.studentmanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.studentmanagement.dto.ResponseWrapper;
import com.example.studentmanagement.dto.ScheduleIdRequest;
import com.example.studentmanagement.models.LessonModel;
import com.example.studentmanagement.services.LessonService;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    @Autowired
    private LessonService lessonService;
    
    @PostMapping("/generate")
    public ResponseEntity<ResponseWrapper<List<LessonModel>>> generateLessons(@RequestBody ScheduleIdRequest  lesson) {
        List<LessonModel> lessons = lessonService.generateLessons(lesson.getScheduleId());
        ResponseWrapper<List<LessonModel>> response = new ResponseWrapper<>(1, "Tạo danh sách tiết học thành công",
                lessons);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<LessonModel>>> getLessonsByScheduleId(@RequestBody ScheduleIdRequest  lesson) {
        List<LessonModel> lessons = lessonService.getLessonsByScheduleId(lesson.getScheduleId());
        ResponseWrapper<List<LessonModel>> response = new ResponseWrapper<>(1, "Lấy danh sách tiết học thành công", lessons);
        return ResponseEntity.ok(response);
    }
}
