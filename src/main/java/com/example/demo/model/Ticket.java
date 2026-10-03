package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Lớp đại diện cho vé xem phim chi tiết (bảng tickets trong ERD)
 * Mỗi vé tương ứng với một ghế cụ thể trong một đơn Booking
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    private int id;
    private Integer bookingId;     // Mã đơn đặt vé tổng (FK trỏ tới Booking)
    private int showtimeId;        // Mã suất chiếu (FK trỏ tới Showtime)
    private Integer seatId;        // Mã ghế trong phòng chiếu (FK trỏ tới Seat)
    private Integer ruleId;        // Mã quy tắc giá vé áp dụng (FK trỏ tới TicketPriceRule)
    private int customerId;        // Mã khách hàng (tương thích ngược)
    private String seatNumber;     // Số ghế hiển thị (VD: "F08", "G12")
    private String seatType;       // STANDARD, VIP, SWEETBOX
    private double totalPrice;     // Giá vé thực tế thanh toán (finalPrice)
    private String ticketCode;     // Mã code vé in ra (VD: "TK-2026-98124")
    private String qrCodeUrl;      // Đường dẫn mã QR để nhân viên quét khi vào rạp
    private String checkinStatus;  // UNCHECKED (Chưa vào rạp), CHECKED_IN (Đã soát vé)
    private LocalDateTime checkedInAt; // Thời điểm soát vé
    private LocalDateTime createdAt;

    // Constructor tương thích ngược cho các đoạn mã hiện có
    public Ticket(int id, int showtimeId, int customerId, String seatNumber, double totalPrice) {
        this.id = id;
        this.showtimeId = showtimeId;
        this.customerId = customerId;
        this.seatNumber = seatNumber;
        this.totalPrice = totalPrice;
        this.seatType = "STANDARD";
        this.checkinStatus = "UNCHECKED";
        this.createdAt = LocalDateTime.now();
    }
}