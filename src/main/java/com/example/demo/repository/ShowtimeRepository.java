package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ShowtimeEntity;

/**
 * JPA Repository: Truy vấn dữ liệu bảng showtimes
 */
@Repository
public interface ShowtimeRepository extends JpaRepository<ShowtimeEntity, Long> {
    List<ShowtimeEntity> findByMovieId(Long movieId);
    List<ShowtimeEntity> findByRoomId(Long roomId);
    List<ShowtimeEntity> findByShowDate(LocalDate showDate);
    List<ShowtimeEntity> findByMovieIdAndShowDate(Long movieId, LocalDate showDate);
    List<ShowtimeEntity> findByStatus(String status);
}
