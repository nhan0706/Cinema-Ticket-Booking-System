package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Thông báo gửi tới người dùng (bảng notifications trong ERD)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private Integer id;
    private Integer userId;        // Khóa ngoại trỏ đến User/Customer
    private String title;          // Tiêu đề: Đặt vé thành công, Ưu đãi mới...
    private String message;        // Nội dung thông báo
    private String type;           // BOOKING, PROMOTION, SYSTEM, REMINDER
    private String channel;        // IN_APP, EMAIL, SMS, PUSH
    private Boolean isRead;        // Đã đọc chưa
    private LocalDateTime sentAt;
    private LocalDateTime readAt;
}
