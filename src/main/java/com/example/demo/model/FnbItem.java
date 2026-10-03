package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Danh mục Bắp nước / Combo F&B (Food & Beverage) của rạp (bảng fnb_items trong ERD)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FnbItem {
    private Integer id;
    private String itemName;       // Tên món: Combo Bắp + Nước, Bắp Phô Mai, Nước ngọt lớn...
    private String category;       // COMBO, POPCORN, DRINK, SNACK
    private String description;    // Mô tả chi tiết món
    private String imageUrl;       // Ảnh minh họa món
    private Double basePrice;      // Giá niêm yết
    private Boolean isActive;      // Đang bán hay tạm ngừng
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public FnbItem(Integer id, String itemName, String category, Double basePrice, Boolean isActive) {
        this.id = id;
        this.itemName = itemName;
        this.category = category;
        this.basePrice = basePrice;
        this.isActive = isActive;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
