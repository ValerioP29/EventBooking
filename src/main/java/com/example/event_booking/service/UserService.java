package com.example.event_booking.service;

import com.example.event_booking.dto.UserDTO;
import com.example.event_booking.exception.ResourceNotFoundException;
import com.example.event_booking.model.User;
import com.example.event_booking.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserDTO.from(user);
    }

    @Transactional
    public List<UserDTO.BookingDTO> getUserBookings(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        return user.getBookedEvents().stream()
                .map(event -> new UserDTO.BookingDTO(event.getId(), event.getTitle(), event.getDate()))
                .collect(Collectors.toList());
    }

}
