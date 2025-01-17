package com.example.event_booking.service;



import com.example.event_booking.dto.EventDTO;
import com.example.event_booking.exception.ResourceNotFoundException;
import com.example.event_booking.model.Event;
import com.example.event_booking.model.User;
import com.example.event_booking.repository.EventRepository;
import com.example.event_booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public EventDTO createEvent(EventDTO eventDto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        Event event = mapToEntity(eventDto);
        event.setCreator(user);
        Event savedEvent = eventRepository.save(event);

        return mapToDto(savedEvent);
    }

    public EventDTO updateEvent(Long eventId, EventDTO eventDto, String username) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));

        if (!event.getCreator().getUsername().equals(username)) {
            throw new IllegalArgumentException("You can only update your own events.");
        }

        event.setTitle(eventDto.getTitle());
        event.setDescription(eventDto.getDescription());
        event.setLocation(eventDto.getLocation());
        event.setDate(eventDto.getDate());
        event.setAvailableSeats(eventDto.getAvailableSeats());

        Event updatedEvent = eventRepository.save(event);

        return mapToDto(updatedEvent);
    }

    public void deleteEvent(Long eventId, String username) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));

        if (!event.getCreator().getUsername().equals(username)) {
            throw new IllegalArgumentException("You can only delete your own events.");
        }

        eventRepository.delete(event);
    }

    private EventDTO mapToDto(Event event) {
        return new EventDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getLocation(),
                event.getDate(),
                event.getAvailableSeats(),
                event.getCreator().getUsername()
        );
    }

    private Event mapToEntity(EventDTO eventDto) {
        Event event = new Event();
        event.setTitle(eventDto.getTitle());
        event.setDescription(eventDto.getDescription());
        event.setLocation(eventDto.getLocation());
        event.setDate(eventDto.getDate());
        event.setAvailableSeats(eventDto.getAvailableSeats());
        return event;
    }
}
