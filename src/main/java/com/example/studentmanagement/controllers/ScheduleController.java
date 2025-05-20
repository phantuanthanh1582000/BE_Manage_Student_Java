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
import com.example.studentmanagement.models.Schedule;
import com.example.studentmanagement.services.ScheduleService;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Schedule>>> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(new ResponseWrapper<>(1, "Lấy danh sách lịch học thành công", schedules));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Schedule>> addSchedule(@RequestBody Schedule schedule) {
        Schedule created = scheduleService.addSchedule(schedule);
        return ResponseEntity.ok(new ResponseWrapper<>(1, "Tạo lịch học thành công", created));
    }
}
