package com.example.studentmanagement.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.studentmanagement.models.Department;
import com.example.studentmanagement.models.Major;
import com.example.studentmanagement.repositories.DepartmentRepository;
import com.example.studentmanagement.repositories.MajorRepository;

@Service
public class MajorService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    private MajorRepository majorRepository;

    MajorService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Major> getAllMajors() {
        return majorRepository.findByIsDelete(false);
    }

    public Major getMajorById(String majorId) {
        return majorRepository.findById(majorId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ngành học với ID: " + majorId));
    }

    public Major addMajor(Major major, String departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa."));

        if (major.getName() == null || major.getName().trim().isEmpty()) {
            throw new RuntimeException("Tên ngành không được để trống.");
        }

        if (major.getMajorCode() == null || major.getMajorCode().trim().isEmpty()) {
            throw new RuntimeException("Mã ngành không được để trống.");
        }

        boolean exists = majorRepository.existsByMajorCodeAndIsDelete(major.getMajorCode(), false);
        if (exists) {
            throw new RuntimeException("Mã ngành đã tồn tại.");
        }

        Major savedMajor = majorRepository.save(major);

        List<String> majorIds = department.getMajorIds();
        if (majorIds == null) {
            majorIds = new ArrayList<>();
        }
        majorIds.add(savedMajor.getId());
        department.setMajorIds(majorIds);
        departmentRepository.save(department);

        return savedMajor;
    }

    public Major updateMajor(String majorId, Major updatedMajor) {
        Major existingMajor = majorRepository.findById(majorId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ngành học"));

        if (updatedMajor.getName() != null && !updatedMajor.getName().isEmpty()) {
            if (majorRepository.existsByName(updatedMajor.getName()) &&
                    !updatedMajor.getName().equals(existingMajor.getName())) {
                throw new RuntimeException("Tên ngành đã tồn tại");
            }
            existingMajor.setName(updatedMajor.getName());
        }

        if (updatedMajor.getMajorCode() != null && !updatedMajor.getMajorCode().isEmpty()) {
            if (majorRepository.existsByMajorCodeAndIsDelete(updatedMajor.getMajorCode(), false) &&
                    !updatedMajor.getMajorCode().equals(existingMajor.getMajorCode())) {
                throw new RuntimeException("Mã ngành đã tồn tại");
            }
            existingMajor.setMajorCode(updatedMajor.getMajorCode());
        }

        return majorRepository.save(existingMajor);
    }

    public void updateIsDeleted(String majorId, String departmentId) {
        Major major = majorRepository.findById(majorId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ngành học."));

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa."));

        major.setDelete(true);
        majorRepository.save(major);

        department.getMajorIds().removeIf(id -> id.equals(majorId));
        departmentRepository.save(department);
    }
}
