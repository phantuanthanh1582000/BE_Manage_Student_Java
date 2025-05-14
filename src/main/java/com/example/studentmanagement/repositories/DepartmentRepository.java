package com.example.studentmanagement.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.studentmanagement.models.Department;

public interface DepartmentRepository extends MongoRepository<Department, String> {
    Optional<Department> findByName(String name);
    Optional<Department> findByDepartmentCode(String departmentCode);
    List<Department> findByIsDeletedFalse();
}
