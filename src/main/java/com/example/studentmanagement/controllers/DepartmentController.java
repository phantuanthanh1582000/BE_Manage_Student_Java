package com.example.studentmanagement.controllers;

import com.example.studentmanagement.models.Department;
import com.example.studentmanagement.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Department> getDepartmentById(@PathVariable String id) {
        return departmentService.getById(id);
    }

    @PostMapping
    public Department createDepartment(@RequestBody Department department) {
        return departmentService.create(department);
    }

    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable String id, @RequestBody Department department) {
        return departmentService.update(id, department);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable String id) {
        departmentService.delete(id);
    }
}
