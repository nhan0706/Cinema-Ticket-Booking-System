package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Lịch sử thay đổi trạng thái đơn đặt vé (bảng booking_status_history trong ERD)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingStatusHistory {
    private Integer id;
    private Integer bookingId;    // Khóa ngoại trỏ đến Booking
    private String oldStatus;     // Trạng thái cũ (VD: PENDING)
    private String newStatus;     // Trạng thái mới (VD: CONFIRMED)
    private String note;          // Ghi chú (VD: Khách hàng thanh toán qua VNPay thành công)
    private String changedBy;     // Người hoặc hệ thống thực hiện đổi trạng thái (SYSTEM, ADMIN, USER)
    private LocalDateTime createdAt;
}
