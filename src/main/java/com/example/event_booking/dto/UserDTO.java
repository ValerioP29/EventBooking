package com.example.event_booking.dto;

import com.example.event_booking.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private Set<BookingDTO> bookings;

    public static UserDTO from(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getBookedEvents().stream()
                        .map(event -> new BookingDTO(event.getId(), event.getTitle(), event.getDate()))
                        .collect(Collectors.toSet())
        );
    }

    @Data
    @AllArgsConstructor
    public static class BookingDTO {
        private Long eventId;
        private String title;
        private LocalDateTime date;
    }
}
