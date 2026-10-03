package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Lớp đại diện cho phòng chiếu phim (bảng cinema_rooms trong ERD)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CinemaRoom {
    private int id;
    private Integer cinemaId;      // Mã rạp chiếu mà phòng trực thuộc (FK trỏ tới Cinema)
    private String roomName;       // Tên phòng chiếu (Ví dụ: Phòng 01, VIP 02, IMAX 01)
    private int totalSeats;        // Tổng số ghế
    private String screenType;     // Loại màn hình/phòng: 2D, 3D, IMAX, 4DX
    private String status;         // ACTIVE, MAINTENANCE, CLOSED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CinemaRoom(int id, String roomName, int totalSeats, String screenType) {
        this.id = id;
        this.roomName = roomName;
        this.totalSeats = totalSeats;
        this.screenType = screenType;
        this.status = "ACTIVE";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public CinemaRoom(int id, Integer cinemaId, String roomName, int totalSeats, String screenType, String status) {
        this.id = id;
        this.cinemaId = cinemaId;
        this.roomName = roomName;
        this.totalSeats = totalSeats;
        this.screenType = screenType;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}