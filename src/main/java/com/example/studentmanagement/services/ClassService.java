package com.example.studentmanagement.services;

import com.example.studentmanagement.models.ClassModel;
import com.example.studentmanagement.models.Major;
import com.example.studentmanagement.repositories.ClassRepository;
import com.example.studentmanagement.repositories.MajorRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private MajorRepository majorRepository;

    public List<ClassModel> getAllClasses() {
        return classRepository.findByIsDeleteFalse();
    }

    public ClassModel getClassById(String classId) {
        return classRepository.findById(classId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp học"));
    }

    public ClassModel addClass(ClassModel classModel, String majorId) {
   
    Major major = majorRepository.findById(majorId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy ngành học"));

    if (classModel.getClassName() == null || classModel.getClassName().trim().isEmpty()) {
        throw new RuntimeException("Tên lớp không được để trống.");
    }

    String prefix = classModel.getClassName().length() >= 4
                    ? classModel.getClassName().substring(0, 4)
                    : classModel.getClassName();
    
    if (!prefix.equalsIgnoreCase(major.getMajorCode())) {
        throw new RuntimeException("Tên lớp không hợp lệ, phải bắt đầu bằng mã ngành.");
    }

    boolean exists = classRepository.existsByClassNameAndIsDelete(classModel.getClassName(), false);
    if (exists) {
        throw new RuntimeException("Tên lớp đã tồn tại.");
    }

    ClassModel savedClass = classRepository.save(classModel);

    List<String> classIds = major.getClassIds(); 
    if (classIds == null) {
        classIds = new ArrayList<>();
    }
    classIds.add(savedClass.getId());
    major.setClassIds(classIds);
    
    majorRepository.save(major);

    return savedClass;
}

public ClassModel updateClass(String classId, ClassModel updatedClassModel, String majorId) {
    
    ClassModel existingClass = classRepository.findById(classId)
        .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp học"));

    Major major = majorRepository.findById(majorId)
        .orElseThrow(() -> new RuntimeException("Không tìm thấy ngành học"));

    String newClassName = updatedClassModel.getClassName();
    if (newClassName == null || newClassName.trim().isEmpty()) {
        throw new RuntimeException("Tên lớp không được để trống.");
    }

    String prefix = newClassName.length() >= 4 ? newClassName.substring(0, 4) : newClassName;
    if (!prefix.equalsIgnoreCase(major.getMajorCode())) {
        throw new RuntimeException("Tên lớp không hợp lệ, phải bắt đầu bằng mã ngành.");
    }

    boolean exists = classRepository.existsByClassNameAndIsDelete(newClassName, false)
        && !existingClass.getClassName().equalsIgnoreCase(newClassName);
    if (exists) {
        throw new RuntimeException("Tên lớp đã tồn tại.");
    }

    existingClass.setClassName(newClassName);

    ClassModel savedClass = classRepository.save(existingClass);

    return savedClass;
} 

public void deleteClass(String classId, String majorId) {
   
    ClassModel classModel = classRepository.findById(classId)
        .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp học"));

    classModel.setDelete(true);
    classRepository.save(classModel);

    Major major = majorRepository.findById(majorId)
        .orElseThrow(() -> new RuntimeException("Không tìm thấy ngành học"));

    List<String> classIds = major.getClassIds();
    if (classIds != null) {
        classIds.remove(classId);
        major.setClassIds(classIds);
        majorRepository.save(major);
    }
}
}
