package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Chi tiết bắp nước được đặt kèm trong đơn Booking (bảng booking_fnb trong ERD)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingFnb {
    private Integer id;
    private Integer bookingId;     // Khóa ngoại trỏ đến Booking
    private Integer itemId;        // Khóa ngoại trỏ đến FnbItem
    private String itemName;       // Tên món (lưu snapshot phòng trường hợp món đổi tên)
    private Integer quantity;      // Số lượng mua
    private Double unitPrice;      // Đơn giá tại thời điểm mua
    private Double totalPrice;     // Thành tiền = quantity * unitPrice
    private String pickupStatus;   // PENDING (Chưa lấy tại quầy), PICKED_UP (Đã nhận bắp nước)
    private LocalDateTime createdAt;
}
