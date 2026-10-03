package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.FnbItemEntity;

/**
 * JPA Repository: Truy vấn dữ liệu bảng fnb_items
 */
@Repository
public interface FnbItemRepository extends JpaRepository<FnbItemEntity, Long> {
    List<FnbItemEntity> findByCategory(String category);
    List<FnbItemEntity> findByIsActive(Boolean isActive);
    List<FnbItemEntity> findByItemNameContainingIgnoreCase(String itemName);
    boolean existsByItemName(String itemName);
}
