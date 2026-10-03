package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Đơn đặt vé / Hóa đơn đặt chỗ của khách hàng (bảng bookings trong ERD)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    private Integer id;
    private String bookingCode;           // Mã đơn đặt (VD: "BK20261001-A9X2")
    private Integer userId;               // Mã người dùng đặt vé (FK trỏ tới User/Customer)
    private Integer showtimeId;           // Suất chiếu đã chọn (FK trỏ tới Showtime)
    private Integer couponId;             // Mã khuyến mãi đã áp dụng (FK trỏ tới Coupon, nếu có)
    private String contactPhone;          // Số điện thoại nhận vé/SMS
    private String contactEmail;          // Email nhận vé điện tử/QR code
    private String status;                // PENDING (Chờ thanh toán), CONFIRMED (Đã đặt thành công), CANCELLED, EXPIRED
    private String paymentStatus;         // UNPAID, PAID, FAILED, REFUNDED
    private String paymentMethod;         // MOMO, VNPAY, ZALOPAY, CREDIT_CARD, CASH
    private String paymentTransactionId;  // Mã giao dịch ngân hàng / cổng thanh toán
    private Double subtotal;              // Tiền vé + Tiền bắp nước gốc
    private Double discountAmount;        // Số tiền giảm giá từ coupon
    private Double totalAmount;           // Tổng số tiền thực tế khách cần thanh toán
    private LocalDateTime holdExpiresAt;  // Thời gian hết hạn giữ chỗ (5-10 phút để thanh toán)
    private LocalDateTime createdAt;
    private LocalDateTime paidAt;         // Thời gian thanh toán thành công
    private LocalDateTime cancelledAt;    // Thời gian đơn bị hủy (nếu có)
}
