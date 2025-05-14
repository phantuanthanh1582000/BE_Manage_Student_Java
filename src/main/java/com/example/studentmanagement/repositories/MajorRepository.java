package com.example.studentmanagement.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.studentmanagement.models.Major;

@Repository
public interface MajorRepository extends MongoRepository<Major, String> {

    boolean existsByMajorCode(String majorCode);

    boolean existsByName(String name);

    Major findByName(String name);

    Major findByMajorCode(String majorCode);

    List<Major> findByIsDelete(boolean isDelete);

}
