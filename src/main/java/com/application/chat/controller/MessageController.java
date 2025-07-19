package com.application.chat.controller;

import com.application.chat.dto.MessageDTO;
import com.application.chat.model.Message;
import com.application.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestBody Map<String, String> payload) {
        String roomId = payload.get("roomId");
        String content = payload.get("content");
        return ResponseEntity.ok(messageService.sendMessage(roomId, content));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<List<MessageDTO>> getRoomMessages(@PathVariable String roomId) {
        return ResponseEntity.ok(messageService.getRoomMessages(roomId));
    }
}
