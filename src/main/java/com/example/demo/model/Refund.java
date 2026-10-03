package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Quản lý hoàn tiền khi đơn vé bị hủy (bảng refunds trong ERD)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Refund {
    private Integer id;
    private Integer bookingId;       // Khóa ngoại trỏ đến Booking
    private Integer approvedBy;      // Mã nhân viên/Admin duyệt hoàn tiền (FK trỏ tới User/Admin)
    private Double refundAmount;     // Số tiền hoàn trả
    private String refundType;       // FULL (Hoàn toàn bộ), PARTIAL (Hoàn một phần)
    private String refundMethod;     // BANK_TRANSFER, ORIGINAL_PAYMENT, LOYALTY_WALLET
    private String transactionId;    // Mã giao dịch hoàn tiền
    private String reason;           // Lý do hoàn vé (Hủy suất chiếu, sự cố kỹ thuật, yêu cầu của khách)
    private String status;           // REQUESTED, PROCESSED, REJECTED
    private LocalDateTime requestedAt;
    private LocalDateTime processedAt;
    private LocalDateTime createdAt;
}
