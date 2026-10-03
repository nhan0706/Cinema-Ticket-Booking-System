package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.CinemaEntity;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CinemaRepository;

/**
 * Service: Xử lý logic nghiệp vụ CRUD cho Rạp chiếu phim (Cinema)
 */
@Service
public class CinemaService {

    private final CinemaRepository cinemaRepository;

    @Autowired
    public CinemaService(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    // 1. Lấy toàn bộ danh sách rạp
    public List<CinemaEntity> getAllCinemas() {
        return cinemaRepository.findAll();
    }

    // 2. Tìm rạp theo ID
    public CinemaEntity getCinemaById(Long id) {
        return cinemaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rạp chiếu phim", "id", id));
    }

    // 3. Tìm rạp theo thành phố
    public List<CinemaEntity> getCinemasByCity(String city) {
        return cinemaRepository.findByCity(city);
    }

    // 4. Tìm kiếm rạp theo tên
    public List<CinemaEntity> searchCinemas(String keyword) {
        return cinemaRepository.findByNameContainingIgnoreCase(keyword);
    }

    // 5. Thêm mới rạp chiếu
    public CinemaEntity createCinema(CinemaEntity cinema) {
        if (cinema.getName() == null || cinema.getName().trim().isEmpty()) {
            throw new BadRequestException("Tên rạp không được để trống!");
        }
        if (cinemaRepository.existsByNameAndAddress(cinema.getName(), cinema.getAddress())) {
            throw new DuplicateResourceException(
                    "Rạp '" + cinema.getName() + "' tại địa chỉ '" + cinema.getAddress() + "' đã tồn tại!");
        }
        return cinemaRepository.save(cinema);
    }

    // 6. Cập nhật rạp
    public CinemaEntity updateCinema(Long id, CinemaEntity cinemaDetails) {
        CinemaEntity existing = getCinemaById(id);
        if (cinemaDetails.getName() == null || cinemaDetails.getName().trim().isEmpty()) {
            throw new BadRequestException("Tên rạp không được để trống!");
        }
        existing.setName(cinemaDetails.getName());
        existing.setAddress(cinemaDetails.getAddress());
        existing.setCity(cinemaDetails.getCity());
        existing.setPhone(cinemaDetails.getPhone());
        existing.setEmail(cinemaDetails.getEmail());
        existing.setStatus(cinemaDetails.getStatus());
        return cinemaRepository.save(existing);
    }

    // 7. Xóa rạp
    public void deleteCinema(Long id) {
        CinemaEntity cinema = getCinemaById(id);
        cinemaRepository.delete(cinema);
    }
}
