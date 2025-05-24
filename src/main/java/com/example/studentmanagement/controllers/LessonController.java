package com.example.studentmanagement.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.studentmanagement.dto.LessonDetailDTO;
import com.example.studentmanagement.dto.ResponseWrapper;
import com.example.studentmanagement.dto.ScheduleIdRequest;
import com.example.studentmanagement.models.JwtUser;
import com.example.studentmanagement.models.LessonModel;
import com.example.studentmanagement.services.LessonService;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @PostMapping("/generate")
    public ResponseEntity<ResponseWrapper<List<LessonModel>>> generateLessons(@RequestBody ScheduleIdRequest lesson) {
        List<LessonModel> lessons = lessonService.generateLessons(lesson.getScheduleId());
        ResponseWrapper<List<LessonModel>> response = new ResponseWrapper<>(1, "Tạo danh sách tiết học thành công",
                lessons);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<LessonModel>>> getLessonsByScheduleId(
            @RequestParam String scheduleId) {
        List<LessonModel> lessons = lessonService.getLessonsByScheduleId(scheduleId);
        ResponseWrapper<List<LessonModel>> response = new ResponseWrapper<>(1, "Lấy danh sách tiết học thành công", lessons);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-date")
    public ResponseEntity<ResponseWrapper<List<LessonDetailDTO>>> getLessonsByDate(
            @RequestParam String date,
            Authentication authentication

    ) {
        JwtUser user = (JwtUser) authentication.getPrincipal(); 
        String teacherId = user.getId();
        // List<LessonModel> lessons = lessonService.getLessonsByTeacherAndDate(teacherId, date);
        List<LessonDetailDTO> lessons =
        lessonService.getLessonsWithScheduleAndClass(teacherId, date);

        // ResponseWrapper<List<LessonModel>> response = new ResponseWrapper<>(1, "Lấy danh sách tiết học thành công",
        //         lessons);
        
        ResponseWrapper<List<LessonDetailDTO>> response = new ResponseWrapper<>(1, "Lấy danh sách tiết học thành công", lessons);
        return ResponseEntity.ok(response);
    }

}
