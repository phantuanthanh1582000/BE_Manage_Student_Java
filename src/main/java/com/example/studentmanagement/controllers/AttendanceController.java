package com.example.studentmanagement.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.studentmanagement.dto.ResponseWrapper;
import com.example.studentmanagement.models.AttendanceModel;
import com.example.studentmanagement.services.AttendanceService;


@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/generate-by-schedule/{scheduleId}")
    public ResponseEntity<?> generateAttendanceBySchedule(@PathVariable String scheduleId) {
        try {
            List<AttendanceModel> result = attendanceService.createBulkAttendanceByScheduleId(scheduleId);
            ResponseWrapper<List<AttendanceModel>> response = new ResponseWrapper<>(
                    1,
                    "Tạo điểm danh cho tất cả lesson trong schedule thành công",
                    result);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/attendance-list/{lessonId}")
    public ResponseEntity<?> getByLessonId(@PathVariable String lessonId) {
        List<AttendanceModel> attendances = attendanceService.getByLessonId(lessonId);
        ResponseWrapper<List<AttendanceModel>> response = new ResponseWrapper<>(1,
                "Lấy danh sách điểm danh thành công",
                attendances);
        return ResponseEntity.ok(response);
    }

}
