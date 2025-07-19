package com.application.chat.controller;

import com.application.chat.dto.RoomDTO;
import com.application.chat.model.Room;
import com.application.chat.model.User;
import com.application.chat.service.RoomService;
import com.application.chat.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Map<String, String> payload) {
        String name = payload.get("name");
        String description = payload.get("description");
        return ResponseEntity.ok(roomService.createRoom(name, description));
    }

    @GetMapping
    public ResponseEntity<List<RoomDTO>> getUserRooms() {
        return ResponseEntity.ok(roomService.getUserRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable String id) {
        User currentUser = userService.getCurrentUser();
        if (!roomService.isUserMemberOfRoom(id, currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return roomService.getRoomById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{roomId}/members")
    public ResponseEntity<Room> addMemberToRoom(@PathVariable String roomId, @RequestBody Map<String, String> payload) {
        String userId = payload.get("userId");
        return ResponseEntity.ok(roomService.addMemberToRoom(roomId, userId));
    }
    
    @PutMapping("/{roomId}/admins")
    public ResponseEntity<Room> promoteToAdmin(@PathVariable String roomId, @RequestBody Map<String, String> payload) {
        String userId = payload.get("userId");
        return ResponseEntity.ok(roomService.promoteToAdmin(roomId, userId));
    }
}