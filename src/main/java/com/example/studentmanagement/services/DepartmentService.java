package com.example.studentmanagement.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.studentmanagement.models.ClassModel;
import com.example.studentmanagement.models.Department;
import com.example.studentmanagement.models.Major;
import com.example.studentmanagement.repositories.ClassRepository;
import com.example.studentmanagement.repositories.DepartmentRepository;
import com.example.studentmanagement.repositories.MajorRepository;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private MajorRepository majorRepository;

    @Autowired
    private ClassRepository classroomRepository;

    public List<Department> getAll() {
        return departmentRepository.findByIsDeletedFalse();
    }

    public Department getById(String id) {
        return departmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa với id: " + id));
    }

    public Department create(Department department) {
        Optional<Department> existingByName = departmentRepository.findByName(department.getName());
        if (existingByName.isPresent()) {
            throw new RuntimeException("Tên khoa đã tồn tại: " + department.getName());
        }

        Optional<Department> existingByCode = departmentRepository.findByDepartmentCode(department.getDepartmentCode());
        if (existingByCode.isPresent()) {
            throw new RuntimeException("Mã khoa đã tồn tại: " + department.getDepartmentCode());
        }

        return departmentRepository.save(department);
    }

    public Department update(String id, Department newDepartment) {
        Department existing = departmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa với id: " + id));

        if (newDepartment.getName() != null && !newDepartment.getName().equals(existing.getName())) {
            Optional<Department> departmentWithName = departmentRepository.findByName(newDepartment.getName());
            if (departmentWithName.isPresent()) {
                throw new RuntimeException("Tên khoa đã tồn tại: " + newDepartment.getName());
            }
            existing.setName(newDepartment.getName());
        }

        if (newDepartment.getDepartmentCode() != null && !newDepartment.getDepartmentCode().equals(existing.getDepartmentCode())) {
            Optional<Department> departmentWithCode = departmentRepository.findByDepartmentCode(newDepartment.getDepartmentCode());
            if (departmentWithCode.isPresent()) {
                throw new RuntimeException("Mã khoa đã tồn tại: " + newDepartment.getDepartmentCode());
            }
            existing.setDepartmentCode(newDepartment.getDepartmentCode());
        }

        return departmentRepository.save(existing);
    }

    @Transactional
    public void delete(String id) {
        Department department = departmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa với id: " + id));

        department.setDeleted(true);
        departmentRepository.save(department);

        List<String> majorIds = department.getMajorIds();
        if (majorIds != null) {
            for (String majorId : majorIds) {
                Major major = majorRepository.findById(majorId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy ngành với id: " + majorId));

                major.setDelete(true);
                majorRepository.save(major);

                List<String> classIds = major.getClassIds();
                if (classIds != null) {
                    for (String classId : classIds) {
                        ClassModel classroom = classroomRepository.findById(classId)
                            .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp với id: " + classId));

                        classroom.setDelete(true);
                        classroomRepository.save(classroom);
                    }
                }
            }
        }
    }
}
