package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Đại diện cho một ghế ngồi trong phòng chiếu (bảng seats trong ERD)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    private Integer id;
    private Integer roomId;        // Khóa ngoại trỏ đến CinemaRoom
    private String seatNumber;     // Tên/mã hiển thị ghế (VD: A01, B05, H10)
    private String seatType;       // STANDARD (Thường), VIP, SWEETBOX (Đôi)
    private Integer rowIndex;      // Chỉ số hàng (VD: Hàng 1 = Hàng A)
    private Integer colIndex;      // Chỉ số cột (VD: Cột 5)
    private LocalDateTime createdAt;

    public Seat(Integer id, Integer roomId, String seatNumber, String seatType, Integer rowIndex, Integer colIndex) {
        this.id = id;
        this.roomId = roomId;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.createdAt = LocalDateTime.now();
    }
}
