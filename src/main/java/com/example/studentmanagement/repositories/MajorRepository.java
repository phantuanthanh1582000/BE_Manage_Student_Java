package com.example.studentmanagement.repositories;

import com.example.studentmanagement.models.Major;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MajorRepository extends MongoRepository<Major, String> {
    Optional<Major> findByName(String name);
    Optional<Major> findByMajorCode(String majorCode);
}
