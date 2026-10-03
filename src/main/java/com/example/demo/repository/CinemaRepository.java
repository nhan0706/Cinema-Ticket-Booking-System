package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CinemaEntity;

/**
 * JPA Repository: Truy vấn dữ liệu bảng cinemas sử dụng Spring Data JPA
 */
@Repository
public interface CinemaRepository extends JpaRepository<CinemaEntity, Long> {
    List<CinemaEntity> findByCity(String city);
    List<CinemaEntity> findByStatus(String status);
    List<CinemaEntity> findByNameContainingIgnoreCase(String name);
    boolean existsByNameAndAddress(String name, String address);
}
