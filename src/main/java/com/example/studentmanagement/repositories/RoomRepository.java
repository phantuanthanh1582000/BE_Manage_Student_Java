package com.example.studentmanagement.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.studentmanagement.models.RoomModel;

@Repository
public interface RoomRepository extends MongoRepository<RoomModel, String> {
    @Override
    List<RoomModel> findAll();
    boolean existsByNameIgnoreCase(String name);
}
