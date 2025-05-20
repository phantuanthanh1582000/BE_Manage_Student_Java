package com.example.studentmanagement.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.studentmanagement.models.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByIsDelete(boolean isDelete);

    boolean existsByEmailAndIsDelete(String email, boolean isDelete);

    User findByEmail(String email);
}
