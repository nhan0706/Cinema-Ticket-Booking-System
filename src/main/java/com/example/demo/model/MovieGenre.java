package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bảng liên kết N-N giữa Phim và Thể loại (bảng movie_genres trong ERD)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieGenre {
    private Integer id;
    private Integer movieId;
    private Integer genreId;
}
