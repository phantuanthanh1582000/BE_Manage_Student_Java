package com.example.studentmanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.studentmanagement.dto.ResponseWrapper;
import com.example.studentmanagement.models.Major;
import com.example.studentmanagement.services.MajorService;

@RestController
@RequestMapping("/api/majors")
public class MajorController {

    @Autowired
    private MajorService majorService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Major>>> getAllMajors() {
        List<Major> majors = majorService.getAllMajors();
        
        ResponseWrapper<List<Major>> response = new ResponseWrapper<>(1, "Lấy danh sách ngành học thành công", majors);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{majorId}")
    public ResponseEntity<ResponseWrapper<Major>> getMajorById(@PathVariable String majorId) {
        Major major = majorService.getMajorById(majorId);
        ResponseWrapper<Major> response = new ResponseWrapper<>(1, "Lấy thông tin ngành học thành công", major);

        return ResponseEntity.ok(response); 
    }

    //Tạo majors
    @PostMapping("/departments/{departmentId}")
    public ResponseEntity<ResponseWrapper<Major>> addMajor(@PathVariable String departmentId, @RequestBody Major major) {
        Major savedMajor = majorService.addMajor(major, departmentId);
        ResponseWrapper<Major> response = new ResponseWrapper<>(1, "Thêm ngành thành công", savedMajor);
        return ResponseEntity.ok(response);
    }

    //Cập nhật Major 
    @PutMapping("/{majorId}")
    public ResponseEntity<ResponseWrapper<Major>> updateMajor(@PathVariable String majorId, @RequestBody Major major) {
        Major updatedMajor = majorService.updateMajor(majorId, major);

        ResponseWrapper<Major> response = new ResponseWrapper<>(1, "Cập nhật ngành thành công", updatedMajor);
        return ResponseEntity.ok(response);
    }

    //Xóa Major khỏi Department
    @DeleteMapping("/{majorId}/departments/{departmentId}")
    public ResponseEntity<ResponseWrapper<String>> deleteMajor(@PathVariable String departmentId, @PathVariable String majorId) {
        majorService.updateIsDeleted(majorId, departmentId);

        ResponseWrapper<String> response = new ResponseWrapper<>(1, "Xóa ngành học thành công", "Ngành học đã xóa khỏi khoa.");
        return ResponseEntity.ok(response);
    }
}
