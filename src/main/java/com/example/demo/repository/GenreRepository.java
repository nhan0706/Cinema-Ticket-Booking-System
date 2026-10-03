package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.GenreEntity;

/**
 * JPA Repository: Truy vấn dữ liệu bảng genres
 */
@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
    boolean existsByName(String name);
    boolean existsBySlug(String slug);
    List<GenreEntity> findByNameContainingIgnoreCase(String name);
}
