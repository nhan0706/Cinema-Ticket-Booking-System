package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bảng quy tắc tính giá vé linh hoạt theo rạp, loại ghế, ngày chiếu, khung giờ (bảng ticket_price_rules trong ERD)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketPriceRule {
    private Integer id;
    private Integer cinemaId;      // Khóa ngoại trỏ đến Cinema (rạp khác nhau có thể có giá khác nhau)
    private String screenType;     // 2D, 3D, IMAX, 4DX
    private String seatType;       // STANDARD, VIP, SWEETBOX
    private String dayType;        // WEEKDAY (Thứ 2-5), WEEKEND (Thứ 6-CN), HOLIDAY (Ngày lễ)
    private String timeSlot;       // MORNING (Trước 12h), PRIME (17h - 22h), NIGHT (Sau 22h)
    private String customerType;   // ADULT (Người lớn), STUDENT (Học sinh/SV), CHILD (Trẻ em), SENIOR (Người cao tuổi)
    private Double price;          // Đơn giá vé áp dụng (VND)
    private Boolean isActive;      // Trạng thái quy tắc giá đang kích hoạt
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
