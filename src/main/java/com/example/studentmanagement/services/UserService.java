package com.example.studentmanagement.services;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.studentmanagement.models.User;
import com.example.studentmanagement.repositories.ClassRepository;
import com.example.studentmanagement.repositories.MajorRepository;
import com.example.studentmanagement.repositories.UserRepository;

@Service
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final ClassRepository classRepository;
    private final MajorRepository majorRepository;
    private final UserRepository userRepository;

    public UserService(ClassRepository classRepository, MajorRepository majorRepository, 
                       BCryptPasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.classRepository = classRepository;
        this.majorRepository = majorRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findByIsDelete(false);
    }

    public User addUser(User user) {
        String role = user.getRole();

        if ("student".equalsIgnoreCase(role)) {
            if (user.getClassId() == null || !classRepository.existsById(user.getClassId())) {
                throw new RuntimeException("Sinh viên phải thuộc một lớp hợp lệ.");
            }
            if (user.getMajorId() == null || !majorRepository.existsById(user.getMajorId())) {
                throw new RuntimeException("Sinh viên phải có một ngành học hợp lệ.");
            }
        } else {
            user.setClassId(null);
            user.setMajorId(null);
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email người dùng không được để trống.");
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new RuntimeException("Mật khẩu không được để trống.");
        }

        boolean exists = userRepository.existsByEmailAndIsDelete(user.getEmail(), false);
        if (exists) {
            throw new RuntimeException("Email người dùng đã tồn tại.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }
}
