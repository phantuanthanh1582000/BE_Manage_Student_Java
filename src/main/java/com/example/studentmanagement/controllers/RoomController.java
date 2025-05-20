package com.example.studentmanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.studentmanagement.dto.ResponseWrapper;
import com.example.studentmanagement.models.RoomModel;
import com.example.studentmanagement.services.RoomService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<RoomModel>>> getAllMajors() {
        List<RoomModel> majors = roomService.getAllRooms();
        
        ResponseWrapper<List<RoomModel>> response = new ResponseWrapper<>(1, "Lấy danh sách phòng thành công", majors);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public  ResponseEntity<ResponseWrapper<RoomModel>> addRoom(@RequestBody RoomModel room) {
        RoomModel savedRoom = roomService.addRoom(room);
        ResponseWrapper<RoomModel> response = new ResponseWrapper<>(1, "Thêm phòng thành công", savedRoom);
        return ResponseEntity.ok(response);
    }
    
}
