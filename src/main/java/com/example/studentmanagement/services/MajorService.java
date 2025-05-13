package com.example.studentmanagement.services;

import com.example.studentmanagement.models.Department;
import com.example.studentmanagement.models.Major;
import com.example.studentmanagement.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department addMajor(String departmentId, Major major) {
    
    Department department = departmentRepository.findById(departmentId)
            .orElseThrow(() -> new RuntimeException("Department not found"));

    if (major.getName() == null || major.getName().trim().isEmpty()) {
        throw new RuntimeException("Tên ngành không được để trống.");
    }

    if (major.getMajorCode() == null || major.getMajorCode().trim().isEmpty()) {
        throw new RuntimeException("Mã ngành không được để trống.");
    }

    boolean exists = department.getMajors().stream()
            .anyMatch(m -> m.getMajorCode().equals(major.getMajorCode()));
    if (exists) {
        throw new RuntimeException("Mã ngành đã tồn tại.");
    }

    department.getMajors().add(major);

    return departmentRepository.save(department);
}

    public Department updateMajor(String departmentId, String majorCode, Major newMajor) {
    
    Department department = departmentRepository.findById(departmentId)
            .orElseThrow(() -> new RuntimeException("Department not found"));

    if (newMajor.getName() == null || newMajor.getName().trim().isEmpty()) {
        throw new RuntimeException("Tên ngành không được để trống.");
    }
    
    boolean nameExists = department.getMajors().stream()
            .anyMatch(major -> major.getName().equalsIgnoreCase(newMajor.getName()) 
                    && !major.getMajorCode().equals(majorCode)); 
    if (nameExists) {
        throw new RuntimeException("Tên ngành đã tồn tại.");
    }

    List<Major> majors = department.getMajors();
    for (int i = 0; i < majors.size(); i++) {
        Major existingMajor = majors.get(i);
        if (existingMajor.getMajorCode().equals(majorCode)) {
            existingMajor.setName(newMajor.getName());
            return departmentRepository.save(department);
        }
    }

    throw new RuntimeException("Major not found");
}

    public Department deleteMajor(String departmentId, String majorCode) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        boolean removed = department.getMajors().removeIf(m -> m.getMajorCode().equals(majorCode));
        if (!removed) throw new RuntimeException("Major not found");

        return departmentRepository.save(department);
    }
}