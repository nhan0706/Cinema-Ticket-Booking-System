package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.CinemaEntity;
import com.example.demo.entity.CinemaRoomEntity;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CinemaRepository;
import com.example.demo.repository.CinemaRoomRepository;

/**
 * Service: Xử lý logic nghiệp vụ CRUD cho Phòng chiếu (CinemaRoom)
 */
@Service
public class CinemaRoomService {

    private final CinemaRoomRepository roomRepository;
    private final CinemaRepository cinemaRepository;

    @Autowired
    public CinemaRoomService(CinemaRoomRepository roomRepository, CinemaRepository cinemaRepository) {
        this.roomRepository = roomRepository;
        this.cinemaRepository = cinemaRepository;
    }

    public List<CinemaRoomEntity> getAllRooms() {
        return roomRepository.findAll();
    }

    public CinemaRoomEntity getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Phòng chiếu", "id", id));
    }

    public List<CinemaRoomEntity> getRoomsByCinemaId(Long cinemaId) {
        return roomRepository.findByCinemaId(cinemaId);
    }

    public CinemaRoomEntity createRoom(Long cinemaId, CinemaRoomEntity room) {
        CinemaEntity cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new ResourceNotFoundException("Rạp chiếu phim", "id", cinemaId));

        if (room.getRoomName() == null || room.getRoomName().trim().isEmpty()) {
            throw new BadRequestException("Tên phòng chiếu không được để trống!");
        }
        if (room.getTotalSeats() == null || room.getTotalSeats() <= 0) {
            throw new BadRequestException("Số ghế phải lớn hơn 0!");
        }
        if (roomRepository.existsByRoomNameAndCinemaId(room.getRoomName(), cinemaId)) {
            throw new DuplicateResourceException(
                    "Phòng '" + room.getRoomName() + "' đã tồn tại trong rạp này!");
        }
        room.setCinema(cinema);
        return roomRepository.save(room);
    }

    public CinemaRoomEntity updateRoom(Long id, CinemaRoomEntity roomDetails) {
        CinemaRoomEntity existing = getRoomById(id);
        if (roomDetails.getRoomName() == null || roomDetails.getRoomName().trim().isEmpty()) {
            throw new BadRequestException("Tên phòng chiếu không được để trống!");
        }
        existing.setRoomName(roomDetails.getRoomName());
        existing.setTotalSeats(roomDetails.getTotalSeats());
        existing.setScreenType(roomDetails.getScreenType());
        existing.setStatus(roomDetails.getStatus());
        return roomRepository.save(existing);
    }

    public void deleteRoom(Long id) {
        CinemaRoomEntity room = getRoomById(id);
        roomRepository.delete(room);
    }
}
