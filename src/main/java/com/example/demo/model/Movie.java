package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Lớp Movie đại diện cho thực thể Phim (bảng movies trong ERD)
 * Đối tượng chính thực hiện CRUD và HATEOAS trong REST API
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Movie extends RepresentationModel<Movie> {
    private Integer id;
    private String title;          // Tên phim
    private String genre;          // Thể loại chính (dùng cho hiển thị nhanh)
    private String description;    // Tóm tắt nội dung phim
    private Integer duration;      // Thời lượng chiếu (phút)
    private String ageRating;      // Phân loại độ tuổi: P (Mọi lứa tuổi), K (Dưới 13 kèm người lớn), T13, T16, T18, C (Cấm)
    private String language;       // Ngôn ngữ: Phụ đề tiếng Việt, Lồng tiếng
    private String posterUrl;      // Ảnh poster phim
    private String trailerUrl;     // Video trailer (Youtube URL)
    private Double price;          // Giá vé cơ bản tham khảo (VND)
    private String status;         // NOW_SHOWING (Đang chiếu), COMING_SOON (Sắp chiếu), ENDED (Ngừng chiếu)
    private LocalDate releaseDate; // Ngày khởi chiếu
    private LocalDate endDate;     // Ngày kết thúc chiếu
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor tương thích ngược cho dữ liệu mẫu và các test case hiện tại
    public Movie(Integer id, String title, String genre, Integer duration, Double price) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.price = price;
        this.status = "NOW_SHOWING";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}