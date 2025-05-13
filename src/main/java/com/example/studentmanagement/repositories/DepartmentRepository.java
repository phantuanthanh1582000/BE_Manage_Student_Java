package com.example.studentmanagement.repositories;

import com.example.studentmanagement.models.Department;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DepartmentRepository extends MongoRepository<Department, String> {
    Optional<Department> findByName(String name);
    Optional<Department> findByDepartmentCode(String departmentCode);
}
