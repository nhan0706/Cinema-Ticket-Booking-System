package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;

/**
 * Service: Xử lý logic nghiệp vụ cho thực thể Phim (Movie)
 */
@Service
public class MovieService {

    private final MovieRepository repository;

    @Autowired
    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    // 1. Lấy toàn bộ danh sách phim
    public List<Movie> getAllMovies() {
        return repository.findAll();
    }

    // 2. Tìm phim theo ID
    public Optional<Movie> getMovieById(int id) {
        return repository.findById(id);
    }

    // 3. Logic thêm phim: Kiểm tra hợp lệ dữ liệu
    public Movie addMovie(Movie movie) {
        if (movie.getTitle() == null || movie.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Ten phim khong duoc de trong!");
        }
        if (movie.getDuration() == null || movie.getDuration() <= 0) {
            throw new IllegalArgumentException("Thoi luong phim phai lon hon 0 phut!");
        }
        if (movie.getPrice() == null || movie.getPrice() <= 0) {
            throw new IllegalArgumentException("Gia ve phim phai lon hon 0 VND!");
        }
        return repository.save(movie);
    }

    // 4. Logic cập nhật thông tin phim
    public Optional<Movie> updateMovie(int id, Movie movieDetails) {
        if (movieDetails.getTitle() == null || movieDetails.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Ten phim khong duoc de trong!");
        }
        if (movieDetails.getDuration() == null || movieDetails.getDuration() <= 0) {
            throw new IllegalArgumentException("Thoi luong phim phai lon hon 0 phut!");
        }
        if (movieDetails.getPrice() == null || movieDetails.getPrice() <= 0) {
            throw new IllegalArgumentException("Gia ve phim phai lon hon 0 VND!");
        }
        return repository.update(id, movieDetails);
    }

    // 5. Logic xóa phim theo ID
    public boolean deleteMovie(int id) {
        return repository.deleteById(id);
    }
}