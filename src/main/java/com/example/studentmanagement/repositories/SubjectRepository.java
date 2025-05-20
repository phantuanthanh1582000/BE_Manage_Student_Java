package com.example.studentmanagement.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.studentmanagement.models.Subject;

public interface SubjectRepository extends MongoRepository<Subject, String> {
    List<Subject> findByIsDeleteFalse();
    boolean existsByNameAndIsDeleteFalse(String name);
    boolean existsByCodeAndIsDeleteFalse(String code);
}
