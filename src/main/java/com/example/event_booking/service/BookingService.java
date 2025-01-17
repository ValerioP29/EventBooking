package com.example.event_booking.service;



import com.example.event_booking.exception.ResourceNotFoundException;
import com.example.event_booking.model.Event;
import com.example.event_booking.model.User;
import com.example.event_booking.repository.EventRepository;
import com.example.event_booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public String bookEvent(Long eventId, String username) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));

        if (event.getAvailableSeats() <= 0) {
            throw new IllegalStateException("No seats available for this event.");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        if (user.getBookedEvents().contains(event)) {
            throw new IllegalStateException("You have already booked this event.");
        }

        user.getBookedEvents().add(event);
        event.setAvailableSeats(event.getAvailableSeats() - 1);

        userRepository.save(user);
        eventRepository.save(event);

        return "Booking confirmed for event: " + event.getTitle();
    }

    public String cancelBooking(Long eventId, String username) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        if (!user.getBookedEvents().contains(event)) {
            throw new IllegalStateException("You have not booked this event.");
        }

        user.getBookedEvents().remove(event);
        event.setAvailableSeats(event.getAvailableSeats() + 1);

        userRepository.save(user);
        eventRepository.save(event);

        return "Booking canceled for event: " + event.getTitle();

    }
    public boolean isEventBookedByUser(Long eventId, String username) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        return user.getBookedEvents().contains(event);
    }
}
