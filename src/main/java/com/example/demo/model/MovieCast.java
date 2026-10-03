package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Diễn viên, đạo diễn của phim (bảng movie_cast trong ERD)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieCast {
    private Integer id;
    private Integer movieId;       // Khóa ngoại trỏ đến Movie
    private String personName;     // Tên nghệ sĩ (VD: Trấn Thành, Tuấn Trần...)
    private String roleType;       // DIRECTOR (Đạo diễn), ACTOR (Diễn viên), PRODUCER (Sản xuất)
    private String characterName;  // Tên nhân vật trong phim
    private Integer displayOrder;  // Thứ tự hiển thị
}
