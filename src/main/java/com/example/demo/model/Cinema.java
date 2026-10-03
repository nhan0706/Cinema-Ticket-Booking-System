package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Đại diện cho một cụm rạp chiếu phim (bảng cinemas trong ERD)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cinema {
    private Integer id;
    private String name;           // Tên rạp (VD: CGV Vincom Bà Triệu, Galaxy Nguyễn Du)
    private String address;        // Địa chỉ chi tiết
    private String city;           // Thành phố (Hà Nội, TP.HCM, Đà Nẵng...)
    private String phone;          // Số điện thoại liên hệ
    private String email;          // Email rạp
    private Double latitude;       // Tọa độ vĩ độ (dùng để định vị rạp gần nhất)
    private Double longitude;      // Tọa độ kinh độ
    private String status;         // ACTIVE, CLOSED, MAINTENANCE
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Cinema(Integer id, String name, String address, String city, String phone, String status) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
