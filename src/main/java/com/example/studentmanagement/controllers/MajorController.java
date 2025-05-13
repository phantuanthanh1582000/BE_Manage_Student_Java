package com.example.studentmanagement.controllers;

import com.example.studentmanagement.models.Department;
import com.example.studentmanagement.models.Major;
import com.example.studentmanagement.services.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/departments/{departmentId}/majors")
public class MajorController {

    @Autowired
    private MajorService majorService;

    @PostMapping
    public Department addMajor(@PathVariable String departmentId, @RequestBody Major major) {
        return majorService.addMajor(departmentId, major);
    }

    @PutMapping("/{majorCode}")
    public Department updateMajor(@PathVariable String departmentId, @PathVariable String majorCode, @RequestBody Major major) {
        return majorService.updateMajor(departmentId, majorCode, major);
    }

    @DeleteMapping("/{majorCode}")
    public Department deleteMajor(@PathVariable String departmentId, @PathVariable String majorCode) {
        return majorService.deleteMajor(departmentId, majorCode);
    }
}