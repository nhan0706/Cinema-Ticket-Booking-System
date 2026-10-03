package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Lớp Customer kế thừa từ lớp cha User, đại diện cho khách hàng đặt vé xem phim
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Customer extends User {
    private int loyaltyPoints;     // Điểm tích lũy thành viên
    private String memberTier;     // Hạng thành viên (STANDARD, SILVER, GOLD, DIAMOND)

    public Customer(int id, String name, String email, String phone, String password, int loyaltyPoints) {
        super(id, name, email, phone, password);
        this.loyaltyPoints = loyaltyPoints;
        this.memberTier = "STANDARD";
    }

    public Customer(int id, String name, String email, String phone, String password, int loyaltyPoints, String memberTier) {
        super(id, name, email, phone, password);
        this.loyaltyPoints = loyaltyPoints;
        this.memberTier = memberTier;
    }
}