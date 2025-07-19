package com.application.chat.controller;

import com.application.chat.dto.UserDTO;
import com.application.chat.model.User;
import com.application.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody User user) {
        String email = user.getEmail();
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!email.matches(emailRegex)) {
            return ResponseEntity.badRequest().body(null);
        }
        User registeredUser = userService.registerUser(user);
        UserDTO userDTO = new UserDTO(registeredUser.getId(), registeredUser.getName(), registeredUser.getEmail());
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/current")
    public ResponseEntity<UserDTO> getCurrentUser() {
        User user = userService.getCurrentUser();
        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail());
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {

        return userService.getUserById(id).map(user -> ResponseEntity.ok(new UserDTO(user.getId(), user.getName(), user.getEmail())))
        .orElse(ResponseEntity.notFound().build());

    }

    //You can only make one MAIN_ADMIN to the DB other can throw error in code
    @PostMapping("/mainAdmin")
    public ResponseEntity<UserDTO> registerMainAdmin(@RequestBody User user) {
    if (userService.mainAdminExists()) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
    User registeredUser = userService.registerMainAdmin(user);
    UserDTO userDTO = new UserDTO(registeredUser.getId(), registeredUser.getName(), registeredUser.getEmail());
    return ResponseEntity.ok(userDTO);
}
}
