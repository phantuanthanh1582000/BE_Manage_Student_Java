package com.example.studentmanagement.repositories;

import java.util.List;


import com.example.studentmanagement.models.RoomModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends MongoRepository<RoomModel, String> {
    List<RoomModel> findAll();

}
