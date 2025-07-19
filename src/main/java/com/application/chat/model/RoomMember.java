package com.application.chat.model;

public class RoomMember {
    private String userId;
    private String role;  // "ADMIN" or "USER"

    public RoomMember() {}
    
    public RoomMember(String userId, String role) {
        this.userId = userId;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}