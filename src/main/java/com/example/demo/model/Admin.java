package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

// Lớp Admin kế thừa từ lớp cha User
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {
    private String role;        // Quyền hạn (SUPER_ADMIN, MANAGER, STAFF)
    private String department;  // Bộ phận (Ban quản lý, Quầy vé, Kỹ thuật)

    public Admin(int id, String name, String email, String phone, String password, String role, String department) {
        super(id, name, email, phone, password);
        this.role = role;
        this.department = department;
    }
}