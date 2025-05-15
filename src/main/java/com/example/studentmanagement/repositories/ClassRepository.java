package com.example.studentmanagement.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.studentmanagement.models.ClassModel;

public interface ClassRepository extends MongoRepository<ClassModel, String> {
    boolean existsByClassNameAndIsDelete(String className, boolean isDelete);
    List<ClassModel> findByIsDeleteFalse();
}
