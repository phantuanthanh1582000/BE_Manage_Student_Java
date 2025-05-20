package com.example.studentmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.studentmanagement.dto.LoginRequest;
import com.example.studentmanagement.dto.ResponseWrapper;
import com.example.studentmanagement.models.User;
import com.example.studentmanagement.security.JwtUtil;
import com.example.studentmanagement.services.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<ResponseWrapper<Map<String, Object>>> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        if (user != null) {
            // Tạo token
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole(), user.getId());

            // Chuẩn bị dữ liệu trả về
            Map<String, Object> data = new HashMap<>();
            data.put("accessToken", token);
            data.put("user", user);

            ResponseWrapper<Map<String, Object>> response = new ResponseWrapper<>(1, "Đăng nhập thành công", data);
            return ResponseEntity.ok(response);
        } else {
            ResponseWrapper<Map<String, Object>> response = new ResponseWrapper<>(0, "Tên đăng nhập hoặc mật khẩu không đúng", null);
            return ResponseEntity.status(401).body(response);
        }
    }
}
