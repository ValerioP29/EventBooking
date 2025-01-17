package com.example.event_booking.controller;



import com.example.event_booking.dto.EventDTO;
import com.example.event_booking.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public List<EventDTO> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping
    public EventDTO createEvent(@RequestBody EventDTO eventDto, @RequestParam String username) {
        return eventService.createEvent(eventDto, username);
    }

    @PutMapping("/{id}")
    public EventDTO updateEvent(@PathVariable Long id, @RequestBody EventDto eventDto, @RequestParam String username) {
        return eventService.updateEvent(id, eventDto, username);
    }

    @DeleteMapping("/{id}")
    public String deleteEvent(@PathVariable Long id, @RequestParam String username) {
        eventService.deleteEvent(id, username);
        return "Event deleted successfully!";
    }
}
