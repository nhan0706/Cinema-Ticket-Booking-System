package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.SeatEntity;

/**
 * JPA Repository: Truy vấn dữ liệu bảng seats
 */
@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, Long> {
    List<SeatEntity> findByRoomId(Long roomId);
    List<SeatEntity> findByRoomIdAndSeatType(Long roomId, String seatType);
    boolean existsBySeatNumberAndRoomId(String seatNumber, Long roomId);
}
