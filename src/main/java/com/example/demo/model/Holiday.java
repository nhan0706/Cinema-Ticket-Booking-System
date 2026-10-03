package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Ngày lễ đặc biệt để tính giá vé phụ thu (bảng holidays trong ERD)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Holiday {
    private Integer id;
    private LocalDate holidayDate; // Ngày lễ (VD: 2026-04-30, 2026-05-01, 2026-09-02)
    private String name;           // Tên ngày lễ
    private LocalDateTime createdAt;
}
