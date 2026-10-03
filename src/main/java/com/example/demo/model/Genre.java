package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Thể loại phim (bảng genres trong ERD)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    private Integer id;
    private String name;           // Tên thể loại: Hành động, Hài, Tình cảm, Kinh dị...
    private String slug;           // Slug URL: hanh-dong, hai, kinh-di
    private LocalDateTime createdAt;

    public Genre(Integer id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.createdAt = LocalDateTime.now();
    }
}
