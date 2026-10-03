package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Lớp cha User đại diện cho người dùng chung trong hệ thống rạp chiếu phim (bảng users trong ERD)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String name;           // Họ và tên (full_name)
    private String email;
    private String phone;
    private String password;       // Mật khẩu (hoặc password_hash)
    private String status;         // ACTIVE, INACTIVE, LOCKED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(int id, String name, String email, String phone, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.status = "ACTIVE";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}