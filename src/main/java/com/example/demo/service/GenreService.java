package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.GenreEntity;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.GenreRepository;

/**
 * Service: Xử lý logic nghiệp vụ CRUD cho Thể loại phim (Genre)
 */
@Service
public class GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<GenreEntity> getAllGenres() {
        return genreRepository.findAll();
    }

    public GenreEntity getGenreById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Thể loại phim", "id", id));
    }

    public GenreEntity createGenre(GenreEntity genre) {
        if (genre.getName() == null || genre.getName().trim().isEmpty()) {
            throw new BadRequestException("Tên thể loại không được để trống!");
        }
        if (genreRepository.existsByName(genre.getName())) {
            throw new DuplicateResourceException("Thể loại '" + genre.getName() + "' đã tồn tại!");
        }
        if (genre.getSlug() == null || genre.getSlug().isEmpty()) {
            genre.setSlug(genre.getName().toLowerCase().replaceAll("[^a-z0-9]+", "-"));
        }
        return genreRepository.save(genre);
    }

    public GenreEntity updateGenre(Long id, GenreEntity genreDetails) {
        GenreEntity existing = getGenreById(id);
        if (genreDetails.getName() == null || genreDetails.getName().trim().isEmpty()) {
            throw new BadRequestException("Tên thể loại không được để trống!");
        }
        existing.setName(genreDetails.getName());
        existing.setSlug(genreDetails.getSlug() != null ? genreDetails.getSlug()
                : genreDetails.getName().toLowerCase().replaceAll("[^a-z0-9]+", "-"));
        return genreRepository.save(existing);
    }

    public void deleteGenre(Long id) {
        GenreEntity genre = getGenreById(id);
        genreRepository.delete(genre);
    }
}
