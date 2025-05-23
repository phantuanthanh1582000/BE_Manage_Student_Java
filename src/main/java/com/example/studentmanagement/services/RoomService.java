package com.example.studentmanagement.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.studentmanagement.models.RoomModel;
import com.example.studentmanagement.repositories.RoomRepository;
import com.example.studentmanagement.utils.statusEnum;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;
    
    // Example method to add a new room
    public RoomModel addRoom(RoomModel room) {
        if (room.getName() == null || room.getName().trim().isEmpty()) {
            throw new RuntimeException("Tên phòng không được để trống.");
        }

        boolean exists = roomRepository.existsByNameIgnoreCase(room.getName().trim());
        if (exists) {
            throw new RuntimeException("Phòng với tên '" + room.getName() + "' đã tồn tại.");
        }

        if (room.getStatus() == null) {
            room.setStatus(statusEnum.ACTIVE.getStatus());
        }
        return roomRepository.save(room);
    }

    public List<RoomModel> getAllRooms() {
        return roomRepository.findAll();
    }
}
