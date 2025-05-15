package com.example.studentmanagement.controllers;

import com.example.studentmanagement.dto.ResponseWrapper;
import com.example.studentmanagement.models.ClassModel;
import com.example.studentmanagement.services.ClassService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classes")
public class ClassController {

    @Autowired
    private ClassService classService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ClassModel>>> getAllClasses() {
        List<ClassModel> classList = classService.getAllClasses();
        ResponseWrapper<List<ClassModel>> response = new ResponseWrapper<>(1, "Lấy danh sách lớp thành công", classList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{classId}")
    public ResponseEntity<ResponseWrapper<ClassModel>> getClassById(@PathVariable String classId) {
        ClassModel classModel = classService.getClassById(classId);
        ResponseWrapper<ClassModel> response = new ResponseWrapper<>(1, "Lấy thông tin lớp thành công", classModel);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/majors/{majorId}")
    public ResponseEntity<ResponseWrapper<ClassModel>> addClass(@PathVariable String majorId, @RequestBody ClassModel classModel) {

        ClassModel savedClass = classService.addClass(classModel, majorId);
        ResponseWrapper<ClassModel> response = new ResponseWrapper<>(1, "Thêm lớp thành công", savedClass);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{classId}/majors/{majorId}")
    public ResponseEntity<ResponseWrapper<ClassModel>> updateClass(@PathVariable String classId, @PathVariable String majorId, @RequestBody ClassModel updatedClassModel) {
        ClassModel updatedClass = classService.updateClass(classId, updatedClassModel, majorId);
        ResponseWrapper<ClassModel> response = new ResponseWrapper<>(1, "Cập nhật lớp thành công", updatedClass);
        return ResponseEntity.ok(response);
        
    }

    @DeleteMapping("/{classId}/majors/{majorId}")
    public ResponseEntity<ResponseWrapper<String>> deleteClass(@PathVariable String classId, @PathVariable String majorId) {
        classService.deleteClass(classId, majorId);
        ResponseWrapper<String> response = new ResponseWrapper<>(1, "Xóa lớp thành công", "Lớp đã được xóa khỏi ngành");
        return ResponseEntity.ok(response);
    }
}
