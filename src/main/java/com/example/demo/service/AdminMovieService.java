package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.MovieEntity;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.MovieJpaRepository;

/**
 * Service: Xử lý logic nghiệp vụ CRUD cho Phim (Movie) - sử dụng JPA
 */
@Service
public class AdminMovieService {

    private final MovieJpaRepository movieRepository;

    @Autowired
    public AdminMovieService(MovieJpaRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<MovieEntity> getAllMovies() {
        return movieRepository.findAll();
    }

    public MovieEntity getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Phim", "id", id));
    }

    public List<MovieEntity> searchMovies(String keyword) {
        return movieRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public List<MovieEntity> getMoviesByStatus(String status) {
        return movieRepository.findByStatus(status);
    }

    public MovieEntity createMovie(MovieEntity movie) {
        if (movie.getTitle() == null || movie.getTitle().trim().isEmpty()) {
            throw new BadRequestException("Tên phim không được để trống!");
        }
        if (movie.getDurationMinutes() == null || movie.getDurationMinutes() <= 0) {
            throw new BadRequestException("Thời lượng phim phải lớn hơn 0 phút!");
        }
        return movieRepository.save(movie);
    }

    public MovieEntity updateMovie(Long id, MovieEntity movieDetails) {
        MovieEntity existing = getMovieById(id);
        if (movieDetails.getTitle() == null || movieDetails.getTitle().trim().isEmpty()) {
            throw new BadRequestException("Tên phim không được để trống!");
        }
        existing.setTitle(movieDetails.getTitle());
        existing.setDescription(movieDetails.getDescription());
        existing.setDurationMinutes(movieDetails.getDurationMinutes());
        existing.setAgeRating(movieDetails.getAgeRating());
        existing.setLanguage(movieDetails.getLanguage());
        existing.setPosterUrl(movieDetails.getPosterUrl());
        existing.setTrailerUrl(movieDetails.getTrailerUrl());
        existing.setStatus(movieDetails.getStatus());
        existing.setReleaseDate(movieDetails.getReleaseDate());
        existing.setEndDate(movieDetails.getEndDate());
        return movieRepository.save(existing);
    }

    public void deleteMovie(Long id) {
        MovieEntity movie = getMovieById(id);
        movieRepository.delete(movie);
    }
}
