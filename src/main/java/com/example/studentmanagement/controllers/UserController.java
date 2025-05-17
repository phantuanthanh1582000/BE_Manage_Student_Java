package com.example.studentmanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.studentmanagement.dto.ResponseWrapper;
import com.example.studentmanagement.models.User;
import com.example.studentmanagement.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<User>>> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        ResponseWrapper<List<User>> response = new ResponseWrapper<>(1, "Lấy danh sách người dùng thành công", userList);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<User>> addUser(@RequestBody User user) {
        User savedUser = userService.addUser(user);
        ResponseWrapper<User> response = new ResponseWrapper<>(1, "Thêm người dùng thành công", savedUser);
        return ResponseEntity.ok(response);
    }
}
