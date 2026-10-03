package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Banner quảng cáo hiển thị trên trang chủ (bảng banners trong ERD)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Banner {
    private Integer id;
    private String title;          // Tiêu đề banner
    private String imageUrl;       // Đường dẫn ảnh banner
    private String linkUrl;        // Đường dẫn khi người dùng bấm vào
    private String targetType;     // MOVIE, PROMOTION, EXTERNAL_LINK
    private Integer targetId;      // ID đối tượng mục tiêu (VD: ID phim hoặc ID coupon)
    private Integer displayOrder;  // Thứ tự hiển thị ưu tiên
    private Boolean isActive;      // Trạng thái kích hoạt
    private LocalDate startDate;   // Ngày bắt đầu chạy banner
    private LocalDate endDate;     // Ngày kết thúc chạy banner
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
