package com.example.event_booking.controller;

import com.example.event_booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final BookingService bookingService;

    @PostMapping("/{eventId}/book")
    public ResponseEntity<String> bookEvent(@PathVariable Long eventId, @RequestParam String username) {
        String response = bookingService.bookEvent(eventId, username);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{eventId}/cancel")
    public ResponseEntity<String> cancelBooking(@PathVariable Long eventId, @RequestParam String username) {
        String response = bookingService.cancelBooking(eventId, username);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{eventId}/isBooked")
    public ResponseEntity<Boolean> isEventBooked(@PathVariable Long eventId, @RequestParam String username) {
        boolean isBooked = bookingService.isEventBookedByUser(eventId, username);
        return ResponseEntity.ok(isBooked);
    }
}
