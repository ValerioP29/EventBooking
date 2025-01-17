package com.example.event_booking.controller;

import com.example.event_booking.dto.UserDTO;
import com.example.event_booking.model.User;
import com.example.event_booking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/{id}/bookings")
    public ResponseEntity<?> getUserBookings(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserBookings(id));
    }
}
