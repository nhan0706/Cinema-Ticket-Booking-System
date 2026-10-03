package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Mã giảm giá / Voucher khuyến mãi (bảng coupons trong ERD)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {
    private Integer id;
    private String code;              // Mã code (VD: CINEMA50, CHAOMUNG)
    private String type;              // PERCENTAGE (Phần trăm), FIXED_AMOUNT (Số tiền cố định)
    private Double value;             // Giá trị giảm (VD: 20.0 nghĩa là 20%, hoặc 50000.0 VND)
    private Double minOrderAmount;    // Đơn hàng tối thiểu để áp dụng mã
    private Double maxDiscount;       // Mức giảm tối đa (nếu là dạng PERCENTAGE)
    private Integer usageLimit;       // Tổng số lượt dùng tối đa của toàn hệ thống
    private Integer usedCount;        // Số lượt đã được sử dụng
    private Integer perUserLimit;     // Số lần tối đa 1 người dùng được sử dụng
    private LocalDateTime validFrom;  // Bắt đầu có hiệu lực
    private LocalDateTime validTo;    // Hết hiệu lực
    private String status;            // ACTIVE, EXPIRED, DISABLED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
