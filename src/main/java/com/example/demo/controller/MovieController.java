package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Movie;
import com.example.demo.service.MovieService;

/**
 * Controller: Cung cấp các RESTful API cho đối tượng Phim (Movie)
 * Hỗ trợ đầy đủ các thao tác CRUD và liên kết HATEOAS
 */
@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "*")
public class MovieController {

    private final MovieService service;

    @Autowired
    public MovieController(MovieService service) {
        this.service = service;
    }

    /**
     * 1. READ ALL - Lấy danh sách tất cả phim (GET /api/movies)
     * Áp dụng HATEOAS: Trả về CollectionModel chứa danh sách kèm liên kết
     */
    @GetMapping
    public CollectionModel<EntityModel<Movie>> getAllMovies() {
        List<EntityModel<Movie>> movies = service.getAllMovies().stream()
                .map(movie -> EntityModel.of(movie,
                        linkTo(methodOn(MovieController.class).getMovieById(movie.getId())).withSelfRel(),
                        linkTo(methodOn(MovieController.class).getAllMovies()).withRel("all-movies")))
                .collect(Collectors.toList());

        return CollectionModel.of(movies,
                linkTo(methodOn(MovieController.class).getAllMovies()).withSelfRel());
    }

    /**
     * 2. READ ONE - Lấy thông tin chi tiết một bộ phim theo ID (GET /api/movies/{id})
     * Áp dụng HATEOAS: Chứa self link, all-movies link, update link, delete link
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Movie>> getMovieById(@PathVariable int id) {
        return service.getMovieById(id)
                .map(movie -> {
                    EntityModel<Movie> resource = EntityModel.of(movie,
                            linkTo(methodOn(MovieController.class).getMovieById(id)).withSelfRel(),
                            linkTo(methodOn(MovieController.class).getAllMovies()).withRel("all-movies"),
                            linkTo(methodOn(MovieController.class).updateMovie(id, movie)).withRel("update"),
                            linkTo(methodOn(MovieController.class).deleteMovie(id)).withRel("delete"));
                    return ResponseEntity.ok(resource);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 3. CREATE - Thêm một bộ phim mới (POST /api/movies)
     * Nhận dữ liệu JSON qua @RequestBody
     */
    @PostMapping
    public ResponseEntity<?> addMovie(@RequestBody Movie movie) {
        try {

            Movie savedMovie = service.addMovie(movie);
            EntityModel<Movie> resource = EntityModel.of(savedMovie,
                    linkTo(methodOn(MovieController.class).getMovieById(savedMovie.getId())).withSelfRel(),
                    linkTo(methodOn(MovieController.class).getAllMovies()).withRel("all-movies"));
            return ResponseEntity.status(HttpStatus.CREATED).body(resource);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * 4. UPDATE - Cập nhật thông tin bộ phim theo ID (PUT /api/movies/{id})
     * Nhận ID qua @PathVariable và dữ liệu cập nhật qua @RequestBody
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        try {
            return service.updateMovie(id, movie)
                    .map(updated -> {
                        EntityModel<Movie> resource = EntityModel.of(updated,
                                linkTo(methodOn(MovieController.class).getMovieById(id)).withSelfRel(),
                                linkTo(methodOn(MovieController.class).getAllMovies()).withRel("all-movies"));
                        return ResponseEntity.ok(resource);
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * 5. DELETE - Xóa bộ phim theo ID (DELETE /api/movies/{id})
     * Nhận ID qua @PathVariable
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable int id) {
        boolean deleted = service.deleteMovie(id);
        if (deleted) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Xoa phim thanh cong voi ID: " + id);
            response.put("deletedId", id);
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Khong tim thay phim voi ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}