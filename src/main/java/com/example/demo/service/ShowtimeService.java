package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ShowtimeEntity;
import com.example.demo.entity.MovieEntity;
import com.example.demo.entity.CinemaRoomEntity;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ShowtimeRepository;
import com.example.demo.repository.MovieJpaRepository;
import com.example.demo.repository.CinemaRoomRepository;

/**
 * Service: Xử lý logic nghiệp vụ CRUD cho Suất chiếu (Showtime)
 */
@Service
public class ShowtimeService {

    private final ShowtimeRepository showtimeRepository;
    private final MovieJpaRepository movieRepository;
    private final CinemaRoomRepository roomRepository;

    @Autowired
    public ShowtimeService(ShowtimeRepository showtimeRepository,
                           MovieJpaRepository movieRepository,
                           CinemaRoomRepository roomRepository) {
        this.showtimeRepository = showtimeRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
    }

    public List<ShowtimeEntity> getAllShowtimes() {
        return showtimeRepository.findAll();
    }

    public ShowtimeEntity getShowtimeById(Long id) {
        return showtimeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Suất chiếu", "id", id));
    }

    public List<ShowtimeEntity> getShowtimesByMovieId(Long movieId) {
        return showtimeRepository.findByMovieId(movieId);
    }

    public ShowtimeEntity createShowtime(Long movieId, Long roomId, ShowtimeEntity showtime) {
        MovieEntity movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Phim", "id", movieId));
        CinemaRoomEntity room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Phòng chiếu", "id", roomId));

        if (showtime.getShowDate() == null) {
            throw new BadRequestException("Ngày chiếu không được để trống!");
        }
        if (showtime.getStartTime() == null) {
            throw new BadRequestException("Giờ bắt đầu chiếu không được để trống!");
        }

        showtime.setMovie(movie);
        showtime.setRoom(room);
        return showtimeRepository.save(showtime);
    }

    public ShowtimeEntity updateShowtime(Long id, ShowtimeEntity showtimeDetails) {
        ShowtimeEntity existing = getShowtimeById(id);
        if (showtimeDetails.getShowDate() != null) existing.setShowDate(showtimeDetails.getShowDate());
        if (showtimeDetails.getStartTime() != null) existing.setStartTime(showtimeDetails.getStartTime());
        if (showtimeDetails.getScreenFormat() != null) existing.setScreenFormat(showtimeDetails.getScreenFormat());
        if (showtimeDetails.getStatus() != null) existing.setStatus(showtimeDetails.getStatus());
        return showtimeRepository.save(existing);
    }

    public void deleteShowtime(Long id) {
        ShowtimeEntity showtime = getShowtimeById(id);
        showtimeRepository.delete(showtime);
    }
}
