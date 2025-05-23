package com.example.studentmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.studentmanagement.dto.ResponseWrapper;
import com.example.studentmanagement.security.JwtAuthenticationFilter;
import com.example.studentmanagement.security.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtUtil);
        ObjectMapper objectMapper = new ObjectMapper();

        http
            .cors(withDefaults())  
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/login").permitAll()
                .anyRequest().hasAnyRole("admin", "teacher")
            )
            .exceptionHandling(exception -> exception
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("application/json;charset=UTF-8");

                    ResponseWrapper<Object> res = new ResponseWrapper<>(0, "Bạn không có quyền truy cập.", null);
                    try {
                        response.getWriter().write(objectMapper.writeValueAsString(res));
                    } catch (JsonProcessingException e) {
                        response.getWriter().write("{\"status\":0,\"message\":\"Lỗi khi xử lý JSON.\"}");
                    }
                })
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json;charset=UTF-8");

                    ResponseWrapper<Object> res = new ResponseWrapper<>(0, "Bạn cần đăng nhập để truy cập.", null);
                    try {
                        response.getWriter().write(objectMapper.writeValueAsString(res));
                    } catch (JsonProcessingException e) {
                        response.getWriter().write("{\"status\":0,\"message\":\"Lỗi khi xử lý JSON.\"}");
                    }
                })
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
