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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.CinemaRoomEntity;
import com.example.demo.service.CinemaRoomService;

/**
 * Admin REST Controller: CRUD API quản lý Phòng chiếu
 */
@RestController
@RequestMapping("/api/admin/rooms")
@CrossOrigin(origins = "*")
public class AdminRoomController {

    private final CinemaRoomService roomService;

    @Autowired
    public AdminRoomController(CinemaRoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<CinemaRoomEntity>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CinemaRoomEntity> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @GetMapping("/cinema/{cinemaId}")
    public ResponseEntity<List<CinemaRoomEntity>> getRoomsByCinema(@PathVariable Long cinemaId) {
        return ResponseEntity.ok(roomService.getRoomsByCinemaId(cinemaId));
    }

    @PostMapping("/cinema/{cinemaId}")
    public ResponseEntity<CinemaRoomEntity> createRoom(@PathVariable Long cinemaId, @RequestBody CinemaRoomEntity room) {
        CinemaRoomEntity created = roomService.createRoom(cinemaId, room);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CinemaRoomEntity> updateRoom(@PathVariable Long id, @RequestBody CinemaRoomEntity room) {
        return ResponseEntity.ok(roomService.updateRoom(id, room));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}
