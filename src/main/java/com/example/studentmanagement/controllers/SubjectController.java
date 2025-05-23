package com.example.studentmanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.studentmanagement.dto.ResponseWrapper;
import com.example.studentmanagement.models.Subject;
import com.example.studentmanagement.services.SubjectService;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Subject>>> getAllSubjects() {
        List<Subject> subjects = subjectService.getAllSubjects();
        ResponseWrapper<List<Subject>> response = new ResponseWrapper<>(1, "Lấy danh sách môn học thành công", subjects);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Subject>> getSubjectById(@PathVariable String id) {
        Subject subject = subjectService.getSubjectById(id);
        ResponseWrapper<Subject> response = new ResponseWrapper<>(1, "Lấy môn học theo ID thành công", subject);
        return ResponseEntity.ok(response);
}

    @PostMapping("/majors/{majorId}")
    public ResponseEntity<ResponseWrapper<Subject>> addSubject(@PathVariable String majorId, @RequestBody Subject subject) {
        Subject savedSubject = subjectService.addSubject(subject, majorId);
        ResponseWrapper<Subject> response = new ResponseWrapper<>(1, "Thêm môn học thành công", savedSubject);
        return ResponseEntity.ok(response);
    }
}
