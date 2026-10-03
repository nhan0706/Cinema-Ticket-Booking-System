package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Movie;

/**
 * Repository: Quản lý lưu trữ dữ liệu Phim trong bộ nhớ List (In-memory storage)
 */
@Repository
public class MovieRepository {
    private final List<Movie> movies = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(0);
    public MovieRepository() {
        save(new Movie(null, "Mai", "Tinh cam / Tam ly", 131, 100000.0));
        save(new Movie(null, "Lat Mat 7: Mot Dieu Uoc", "Gia dinh / Hai", 138, 95000.0));
        save(new Movie(null, "Dao, Pho va Piano", "Lich su / Chien tranh", 100, 85000.0));
        save(new Movie(null, "Conan: Ngoi Sao 5 Canh", "Hoat hinh / Trinh tham", 110, 90000.0));
    }

    // 1. Lấy toàn bộ danh sách phim
    public List<Movie> findAll() {
        return new ArrayList<>(movies);
    }

    // 2. Tìm phim theo ID
    public Optional<Movie> findById(int id) {
        return movies.stream()
                .filter(m -> m.getId() != null && m.getId() == id)
                .findFirst();
    }

    // 3. Thêm mới phim (tự động tăng ID nếu id chưa có)
    public Movie save(Movie movie) {
        if (movie.getId() == null || movie.getId() <= 0) {
            movie.setId(idGenerator.incrementAndGet());
        } else {
            if (movie.getId() > idGenerator.get()) {
                idGenerator.set(movie.getId());
            }
        }
        movies.add(movie);
        return movie;
    }

    // 4. Cập nhật thông tin phim theo ID
    public Optional<Movie> update(int id, Movie updatedMovie) {
        for (int i = 0; i < movies.size(); i++) {
            Movie existing = movies.get(i);
            if (existing.getId() != null && existing.getId() == id) {
                updatedMovie.setId(id);
                movies.set(i, updatedMovie);
                return Optional.of(updatedMovie);
            }
        }
        return Optional.empty();
    }

    // 5. Xóa phim theo ID
    public boolean deleteById(int id) {
        return movies.removeIf(m -> m.getId() != null && m.getId() == id);
    }
}