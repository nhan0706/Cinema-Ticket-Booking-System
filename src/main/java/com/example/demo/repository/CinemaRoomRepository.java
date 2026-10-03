package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CinemaRoomEntity;

/**
 * JPA Repository: Truy vấn dữ liệu bảng cinema_rooms
 */
@Repository
public interface CinemaRoomRepository extends JpaRepository<CinemaRoomEntity, Long> {
    List<CinemaRoomEntity> findByCinemaId(Long cinemaId);
    List<CinemaRoomEntity> findByStatus(String status);
    List<CinemaRoomEntity> findByScreenType(String screenType);
    boolean existsByRoomNameAndCinemaId(String roomName, Long cinemaId);
}
