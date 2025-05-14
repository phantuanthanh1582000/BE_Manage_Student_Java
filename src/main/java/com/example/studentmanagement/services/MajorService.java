package com.example.studentmanagement.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.studentmanagement.models.Major;
import com.example.studentmanagement.repositories.MajorRepository;

@Service
public class MajorService {

    @Autowired
    private MajorRepository majorRepository;

     public List<Major> getAllMajors() {
        return majorRepository.findByIsDelete(false); 
    }

    public Major getMajorById(String majorId) {
        return majorRepository.findById(majorId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ngành học với ID: " + majorId));
    }

    // Thêm Major mới vào collection majors
    public Major addMajor(Major major) {
        if (major.getName() == null || major.getName().trim().isEmpty()) {
            throw new RuntimeException("Tên ngành không được để trống.");
        }
        
        if (major.getMajorCode() == null || major.getMajorCode().trim().isEmpty()) {
            throw new RuntimeException("Mã ngành không được để trống.");
        }

        boolean exists = majorRepository.existsByMajorCode(major.getMajorCode());
        if (exists) {
            throw new RuntimeException("Mã ngành đã tồn tại.");
        }

        return majorRepository.save(major);
    }

    // Cập nhật thông tin Major theo ID
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
        if (majorRepository.existsByMajorCode(updatedMajor.getMajorCode()) && 
            !updatedMajor.getMajorCode().equals(existingMajor.getMajorCode())) {
            throw new RuntimeException("Mã ngành đã tồn tại");
        }
        existingMajor.setMajorCode(updatedMajor.getMajorCode());
    }

    return majorRepository.save(existingMajor);
}

    public void updateIsDeleted(String majorId) {
    Major major = majorRepository.findById(majorId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy ngành học"));

    major.setDelete(true);

    majorRepository.save(major);
}
}
