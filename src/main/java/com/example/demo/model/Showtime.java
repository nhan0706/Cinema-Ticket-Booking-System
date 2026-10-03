package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Lớp đại diện cho suất chiếu phim (bảng showtimes trong ERD)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Showtime {
    private int id;
    private int movieId;           // Mã phim được chiếu (FK trỏ tới Movie)
    private int roomId;            // Mã phòng chiếu (FK trỏ tới CinemaRoom)
    private LocalDate showDate;    // Ngày chiếu
    private String startTime;      // Giờ bắt đầu chiếu (VD: "19:30" hoặc "2026-10-01 19:30")
    private String screenFormat;   // Định dạng chiếu: 2D Phụ đề, 3D Lồng tiếng, IMAX 2D...
    private String status;         // OPEN (Đang mở bán vé), CLOSED (Đã đóng suất)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Showtime(int id, int movieId, int roomId, String startTime) {
        this.id = id;
        this.movieId = movieId;
        this.roomId = roomId;
        this.startTime = startTime;
        this.showDate = LocalDate.now();
        this.screenFormat = "2D";
        this.status = "OPEN";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}