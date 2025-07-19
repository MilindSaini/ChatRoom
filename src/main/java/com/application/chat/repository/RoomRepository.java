package com.application.chat.repository;

import com.application.chat.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface RoomRepository extends MongoRepository<Room, String> {
    List<Room> findByAdminId(String adminId);
}
