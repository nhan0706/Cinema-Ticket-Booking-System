package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.CinemaEntity;
import com.example.demo.service.CinemaService;

/**
 * Admin REST Controller: CRUD API quản lý Rạp chiếu phim
 */
@RestController
@RequestMapping("/api/admin/cinemas")
@CrossOrigin(origins = "*")
public class AdminCinemaController {

    private final CinemaService cinemaService;

    @Autowired
    public AdminCinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    // GET /api/admin/cinemas - Lấy toàn bộ danh sách rạp
    @GetMapping
    public ResponseEntity<List<CinemaEntity>> getAllCinemas() {
        return ResponseEntity.ok(cinemaService.getAllCinemas());
    }

    // GET /api/admin/cinemas/{id} - Lấy chi tiết rạp theo ID
    @GetMapping("/{id}")
    public ResponseEntity<CinemaEntity> getCinemaById(@PathVariable Long id) {
        return ResponseEntity.ok(cinemaService.getCinemaById(id));
    }

    // GET /api/admin/cinemas/search?keyword=... - Tìm kiếm rạp
    @GetMapping("/search")
    public ResponseEntity<List<CinemaEntity>> searchCinemas(@RequestParam String keyword) {
        return ResponseEntity.ok(cinemaService.searchCinemas(keyword));
    }

    // GET /api/admin/cinemas/city/{city} - Lọc rạp theo thành phố
    @GetMapping("/city/{city}")
    public ResponseEntity<List<CinemaEntity>> getCinemasByCity(@PathVariable String city) {
        return ResponseEntity.ok(cinemaService.getCinemasByCity(city));
    }

    // POST /api/admin/cinemas - Thêm mới rạp
    @PostMapping
    public ResponseEntity<CinemaEntity> createCinema(@RequestBody CinemaEntity cinema) {
        CinemaEntity created = cinemaService.createCinema(cinema);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT /api/admin/cinemas/{id} - Cập nhật rạp
    @PutMapping("/{id}")
    public ResponseEntity<CinemaEntity> updateCinema(@PathVariable Long id, @RequestBody CinemaEntity cinema) {
        return ResponseEntity.ok(cinemaService.updateCinema(id, cinema));
    }

    // DELETE /api/admin/cinemas/{id} - Xóa rạp
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCinema(@PathVariable Long id) {
        cinemaService.deleteCinema(id);
        return ResponseEntity.noContent().build();
    }
}
