package com.example.studentmanagement.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.studentmanagement.models.Major;
import com.example.studentmanagement.models.Subject;
import com.example.studentmanagement.repositories.MajorRepository;
import com.example.studentmanagement.repositories.SubjectRepository;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private MajorRepository majorRepository;

    // Lấy tất cả subject chưa bị xóa
    public List<Subject> getAllSubjects() {
        return subjectRepository.findByIsDeleteFalse();
    }

    public Subject getSubjectById(String id) {
        return subjectRepository.findById(id)
                .filter(subject -> !subject.isDelete())
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy môn học với ID: " + id));
    }

    // Thêm mới subject với majorId
    public Subject addSubject(Subject subject, String majorId) {
        if (subject.getName() == null || subject.getName().trim().isEmpty()) {
            throw new RuntimeException("Tên môn học không được để trống");
        }
        if (subject.getCode() == null || subject.getCode().trim().isEmpty()) {
            throw new RuntimeException("Mã môn học không được để trống");
        }

        // Lấy major theo majorId
        Optional<Major> majorOpt = majorRepository.findById(majorId);
        if (!majorOpt.isPresent()) {
            throw new RuntimeException("Ngành không tồn tại");
        }

        Major major = majorOpt.get();
        String majorCode = major.getMajorCode();

        if (majorCode == null || majorCode.trim().isEmpty()) {
            throw new RuntimeException("Mã ngành không hợp lệ");
        }

        String code = subject.getCode().trim();

        // Kiểm tra mã môn học phải bắt đầu bằng mã ngành
        if (!code.startsWith(majorCode.trim())) {
            throw new RuntimeException("Mã môn học phải bắt đầu bằng mã ngành " + majorCode);
        }

        // Kiểm tra trùng tên
        if (subjectRepository.existsByNameAndIsDeleteFalse(subject.getName().trim())) {
            throw new RuntimeException("Tên môn học đã tồn tại");
        }

        // Kiểm tra trùng code
        if (subjectRepository.existsByCodeAndIsDeleteFalse(code)) {
            throw new RuntimeException("Mã môn học đã tồn tại");
        }

        return subjectRepository.save(subject);
    }
}
