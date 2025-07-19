package com.application.chat.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    
    private String name;
    
    private String password;
    
    @Indexed(unique = true)
    private String email;
    
    private List<String> rooms = new ArrayList<>();

    private String role;
    public User() {}
    
    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRooms() {
        return rooms;
    }

    public void setRooms(List<String> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(String roomId) {
        this.rooms.add(roomId);
    }
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
}



// Here are the API endpoints along with their sample inputs for testing:

// AuthController
// Register User

// Endpoint: POST /api/auth/register
// Sample Input:
// {
//     "name": "John Doe",
//     "email": "john.doe@example.com",
//     "password": "password123"
//   }
//   Register Main Admin

// Endpoint: POST /api/auth/mainAdmin
// Sample Input:
// {
//     "name": "Admin User",
//     "email": "admin@example.com",
//     "password": "adminpassword"
//   }
//   Get Current User

// Endpoint: GET /api/auth/current
// Sample Input: None
// Get All Users

// Endpoint: GET /api/auth/users
// Sample Input: None
// Get User by ID

// Endpoint: GET /api/auth/users/{id}
// Sample Input: None
// RoomController
// Create Room

// Endpoint: POST /api/rooms
// Sample Input:
// {
//     "name": "General",
//     "description": "General discussion room"
//   }
//   Get User Rooms

// Endpoint: GET /api/rooms
// Sample Input: None
// Get Room by ID

// Endpoint: GET /api/rooms/{id}
// Sample Input: None
// Add Member to Room

// Endpoint: POST /api/rooms/{roomId}/members
// Sample Input:
// {
//     "userId": "user123"
//   }
//   Promote Member to Admin

// Endpoint: PUT /api/rooms/{roomId}/admins
// Sample Input:
// {
//     "userId": "user123"
//   }
//   MessageController
// Send Message

// Endpoint: POST /api/messages
// Sample Input:
// {
//     "roomId": "room123",
//     "content": "Hello, everyone!"
//   }
//   Get Room Messages

// Endpoint: GET /api/messages/{roomId}
// Sample Input: None