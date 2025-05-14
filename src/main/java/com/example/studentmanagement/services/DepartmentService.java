package com.example.studentmanagement.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.studentmanagement.models.Department;
import com.example.studentmanagement.repositories.DepartmentRepository;
import com.example.studentmanagement.repositories.MajorRepository;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private MajorRepository majorRepository;

    public List<Department> getAll() {
    return departmentRepository.findByIsDeletedFalse(); 
}

public Department addMajorToDepartment(String departmentId, String majorId) {
        // Tìm Department theo ID
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        // Kiểm tra nếu Major đã tồn tại
        if (!majorRepository.existsById(majorId)) {
            throw new RuntimeException("Major not found");
        }

        // Thêm ID của Major vào danh sách majors trong Department (nếu chưa có)
        if (!department.getMajorIds().contains(majorId)) {
            department.getMajorIds().add(majorId);
        }

        // Lưu lại Department với Major đã được thêm
        return departmentRepository.save(department);
    }

    public Department getById(String id) {
    return departmentRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Không tìm thấy ngành với id: " + id));
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
    Department existing = departmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));

    existing.setDeleted(true); 
    departmentRepository.save(existing); 
}

    public Department removeMajorFromDepartment(String departmentId, String majorId) {
    
    Department department = departmentRepository.findById(departmentId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa"));

    department.getMajorIds().removeIf(id -> id.equals(majorId));

    return departmentRepository.save(department);
}

}
