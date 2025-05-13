package com.example.studentmanagement.services;

import com.example.studentmanagement.models.Department;
import com.example.studentmanagement.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    public Optional<Department> getById(String id) {
    Department existing = departmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
    return Optional.of(existing);  
}

    public Department create(Department department) {
    
    Optional<Department> existingByName = departmentRepository.findByName(department.getName());
    if (existingByName.isPresent()) {
        throw new RuntimeException("Tên ngành đã tồn tại: " + department.getName());
    }

    Optional<Department> existingByCode = departmentRepository.findByDepartmentCode(department.getDepartmentCode());
    if (existingByCode.isPresent()) {
        throw new RuntimeException("Mã ngành đã tồn tại: " + department.getDepartmentCode());
    }

    return departmentRepository.save(department);
}

    public Department update(String id, Department newDepartment) {
    
    Department existing = departmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));

    
    if (newDepartment.getName() != null && !newDepartment.getName().equals(existing.getName())) {
        Optional<Department> departmentWithName = departmentRepository.findByName(newDepartment.getName());
        if (departmentWithName.isPresent()) {
            throw new RuntimeException("Tên ngành đã tồn tại: " + newDepartment.getName());
        }
        existing.setName(newDepartment.getName());
    }

    
    if (newDepartment.getDepartmentCode() != null && !newDepartment.getDepartmentCode().equals(existing.getDepartmentCode())) {
        Optional<Department> departmentWithCode = departmentRepository.findByDepartmentCode(newDepartment.getDepartmentCode());
        if (departmentWithCode.isPresent()) {
            throw new RuntimeException("Mã ngành đã tồn tại: " + newDepartment.getDepartmentCode());
        }
        existing.setDepartmentCode(newDepartment.getDepartmentCode());
    }

    return departmentRepository.save(existing);
}


    public void delete(String id) {
        if (!departmentRepository.existsById(id)) {
            throw new RuntimeException("Department not found with id: " + id);
        }
        departmentRepository.deleteById(id);
    }
}
