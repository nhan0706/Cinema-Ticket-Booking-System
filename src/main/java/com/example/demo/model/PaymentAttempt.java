package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Lịch sử các lần thử thanh toán qua cổng điện tử (bảng payment_attempts trong ERD)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAttempt {
    private Integer id;
    private Integer bookingId;        // Khóa ngoại trỏ đến Booking
    private String paymentMethod;     // MOMO, VNPAY, ZALOPAY, CREDIT_CARD
    private String transactionId;     // Mã giao dịch từ cổng thanh toán
    private Double amount;            // Số tiền thanh toán
    private String status;            // SUCCESS, FAILED, PENDING
    private String errorCode;         // Mã lỗi nếu thất bại (từ cổng thanh toán)
    private String errorMessage;      // Thông báo lỗi chi tiết
    private String gatewayResponse;   // Payload thô phản hồi từ đối tác thanh toán
    private LocalDateTime createdAt;
}
