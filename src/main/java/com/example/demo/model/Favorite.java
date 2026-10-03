package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Danh sách phim yêu thích của người dùng (bảng favorites trong ERD)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {
    private Integer id;
    private Integer userId;        // Khóa ngoại trỏ đến User/Customer
    private Integer movieId;       // Khóa ngoại trỏ đến Movie
    private LocalDateTime createdAt;

    public Favorite(Integer id, Integer userId, Integer movieId) {
        this.id = id;
        this.userId = userId;
        this.movieId = movieId;
        this.createdAt = LocalDateTime.now();
    }
}
