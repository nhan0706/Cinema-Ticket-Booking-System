package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.MovieEntity;

/**
 * JPA Repository: Truy vấn dữ liệu bảng movies sử dụng Spring Data JPA
 */
@Repository
public interface MovieJpaRepository extends JpaRepository<MovieEntity, Long> {
    List<MovieEntity> findByStatus(String status);
    List<MovieEntity> findByTitleContainingIgnoreCase(String title);
    List<MovieEntity> findByAgeRating(String ageRating);
    boolean existsByTitle(String title);
}
