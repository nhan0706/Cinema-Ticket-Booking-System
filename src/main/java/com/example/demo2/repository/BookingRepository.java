package com.example.demo2.repository;

import com.example.demo2.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByBookingCode(String bookingCode);

    List<Booking> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Booking> findByShowtimeIdOrderByCreatedAtDesc(Long showtimeId);

    List<Booking> findByStatusOrderByCreatedAtDesc(String status);

    long countByUserIdAndCouponIdAndStatusNot(Long userId, Long couponId, String excludedStatus);

    @Query("SELECT b FROM Booking b WHERE b.status = 'pending' AND b.holdExpiresAt IS NOT NULL AND b.holdExpiresAt < :now")
    List<Booking> findExpiredPendingBookings(@Param("now") LocalDateTime now);
}
