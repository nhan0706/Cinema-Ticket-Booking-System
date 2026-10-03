package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Đánh giá và bình luận phim của người dùng (bảng reviews trong ERD)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private Integer id;
    private Integer userId;        // Khóa ngoại trỏ đến User/Customer
    private Integer movieId;       // Khóa ngoại trỏ đến Movie
    private Integer rating;        // Điểm đánh giá (1 - 5 sao hoặc 1 - 10)
    private String comment;        // Nội dung cảm nhận của khán giả
    private String status;         // PENDING (Chờ duyệt), APPROVED (Hiển thị), REJECTED (Vi phạm)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
