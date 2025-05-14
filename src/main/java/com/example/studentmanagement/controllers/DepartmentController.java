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
import com.example.studentmanagement.models.Department;
import com.example.studentmanagement.services.DepartmentService;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Department>>> getAllDepartments() {
        List<Department> departments = departmentService.getAll();
        ResponseWrapper<List<Department>> response = new ResponseWrapper<>(1,"Thành công", departments);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable String id) {
        Department department = departmentService.getById(id);
        return ResponseEntity.ok(new ResponseWrapper<>(1,"Lấy ngành thành công", department));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Department>> createDepartment(@RequestBody Department department) {
        Department createdDepartment = departmentService.create(department);
        ResponseWrapper<Department> response = new ResponseWrapper<>(1,"Tạo khoa thành công", createdDepartment);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Department>> updateDepartment(@PathVariable String id, @RequestBody Department department) {
        Department updatedDepartment = departmentService.update(id, department);
        ResponseWrapper<Department> response = new ResponseWrapper<>(1,"Cập nhật khoa thành công", updatedDepartment);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<String>> deleteDepartment(@PathVariable String id) {
        departmentService.delete(id);
        ResponseWrapper<String> response = new ResponseWrapper<>(1,"Xóa khoa thành công", "Với id khoa " + id + " đã được xóa.");
        return ResponseEntity.ok(response);
    }
}
