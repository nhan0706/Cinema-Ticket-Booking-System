package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Lịch sử biến động điểm tích lũy thành viên (bảng loyalty_transactions trong ERD)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoyaltyTransaction {
    private Integer id;
    private Integer userId;        // Khóa ngoại trỏ đến User/Customer
    private Integer bookingId;     // Khóa ngoại trỏ đến Booking (nếu tích điểm từ đơn hàng)
    private String type;           // EARN (Cộng điểm), REDEEM (Tiêu điểm), EXPIRE (Hết hạn), ADJUST (Điều chỉnh)
    private Integer points;        // Số điểm biến động (+100, -50...)
    private Integer balanceAfter;  // Số dư điểm sau giao dịch
    private String description;    // Diễn giải chi tiết
    private LocalDateTime createdAt;
}
