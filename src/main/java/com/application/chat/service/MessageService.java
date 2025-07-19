package com.application.chat.service;

import com.application.chat.dto.MessageDTO;
import com.application.chat.model.Message;
import com.application.chat.model.Room;
import com.application.chat.model.User;
import com.application.chat.repository.MessageRepository;
import com.application.chat.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserService userService;

    public Message sendMessage(String roomId, String content) {
        User currentUser = userService.getCurrentUser();
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        
        // Check if user is a member of the room
        if (!room.isMember(currentUser.getId())) {
            throw new RuntimeException("User is not a member of this room");
        }
        
        Message message = new Message(content, roomId, currentUser.getId(), currentUser.getName());
        return messageRepository.save(message);
    }

    public List<MessageDTO> getRoomMessages(String roomId) {
        User currentUser = userService.getCurrentUser();
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        
        // Check if user is a member of the room
        if (!room.isMember(currentUser.getId())) {
            throw new RuntimeException("User is not a member of this room");
        }
        
        return messageRepository.findByRoomIdOrderByCreatedAtAsc(roomId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private MessageDTO convertToDTO(Message message) {
        MessageDTO dto = new MessageDTO();
        dto.setId(message.getId());
        dto.setContent(message.getContent());
        dto.setRoomId(message.getRoomId());
        dto.setSenderId(message.getSenderId());
        dto.setSenderName(message.getSenderName());
        dto.setCreatedAt(message.getCreatedAt());
        return dto;
    }
}
