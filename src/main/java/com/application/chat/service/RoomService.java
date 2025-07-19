package com.application.chat.service;

import com.application.chat.dto.RoomDTO;
import com.application.chat.model.Room;
import com.application.chat.model.RoomMember;
import com.application.chat.model.User;
import com.application.chat.repository.RoomRepository;
import com.application.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public Room createRoom(String name, String description) {
        User currentUser = userService.getCurrentUser();
        Room room = new Room(name, description, currentUser.getId());
        
        // Add admin as a member with ADMIN role
        RoomMember adminMember = new RoomMember(currentUser.getId(), "ADMIN");
        room.addMember(adminMember);
        
        // Find the MAIN_ADMIN user and add them as an ADMIN in the room
        User mainAdmin = userRepository.findByRole("MAIN_ADMIN")
        .orElseThrow(() -> new RuntimeException("MAIN_ADMIN user not found"));
            RoomMember mainAdminMember = new RoomMember(mainAdmin.getId(), "ADMIN");
        room.addMember(mainAdminMember);
        Room savedRoom = roomRepository.save(room);
        
        // Update user's room list
        currentUser.addRoom(savedRoom.getId());
        userRepository.save(currentUser);
        
        mainAdmin.addRoom(savedRoom.getId());
        userRepository.save(mainAdmin);
        return savedRoom;
    }

    public List<RoomDTO> getUserRooms() {
        User currentUser = userService.getCurrentUser();
        return roomRepository.findAll().stream()
                .filter(room -> room.isMember(currentUser.getId()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<Room> getRoomById(String id) {
        return roomRepository.findById(id);
    }

    public Room addMemberToRoom(String roomId, String userId) {
        User currentUser = userService.getCurrentUser();
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        
        // Check if current user is admin
        if (!room.isAdmin(currentUser.getId())) {
            throw new RuntimeException("Only admin can add members");
        }
        
        // Check if user exists
        User userToAdd = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Check if user is already a member
        if (room.isMember(userId)) {
            throw new RuntimeException("User is already a member");
        }
        
        // Add user to room with USER role
        RoomMember member = new RoomMember(userId, "USER");
        room.addMember(member);
        
        // Update user's room list
        userToAdd.addRoom(roomId);
        userRepository.save(userToAdd);
        
        return roomRepository.save(room);
    }
    public Room promoteToAdmin(String roomId, String userId) {
        User currentUser = userService.getCurrentUser();
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        
        // Check if current user is admin
        if (!room.isAdmin(currentUser.getId())) {
            throw new RuntimeException("Only admins can promote members to admin");
        }
        
        // Check if target user is a member but not already an admin
        boolean userFound = false;
        for (RoomMember member : room.getMembers()) {
            if (member.getUserId().equals(userId)) {
                if ("ADMIN".equals(member.getRole())) {
                    throw new RuntimeException("User is already an admin");
                }
                member.setRole("ADMIN");
                userFound = true;
                break;
            }
        }
        
        if (!userFound) {
            throw new RuntimeException("User is not a member of this room");
        }
        
        return roomRepository.save(room);
    }
    
    public boolean isUserMemberOfRoom(String roomId, String userId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        return room.isMember(userId);
    }

    private RoomDTO convertToDTO(Room room) {
        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setDescription(room.getDescription());
        dto.setAdminId(room.getAdminId());
        dto.setMembers(room.getMembers());
        dto.setCreatedAt(room.getCreatedAt());
        return dto;
    }
}