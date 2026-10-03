package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Thiết bị đăng nhập của người dùng để gửi push notification (bảng user_devices trong ERD)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDevice {
    private Integer id;
    private Integer userId;        // Khóa ngoại trỏ đến User
    private String deviceToken;    // Token thiết bị FCM / APNS
    private String platform;       // IOS, ANDROID, WEB
    private String deviceName;     // VD: iPhone 15 Pro, Samsung Galaxy S24
    private Boolean isActive;      // Trạng thái thiết bị đang hoạt động
    private LocalDateTime lastActiveAt;
    private LocalDateTime createdAt;
}
