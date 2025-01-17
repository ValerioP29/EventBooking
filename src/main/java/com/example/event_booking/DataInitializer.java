package com.example.event_booking;

import com.example.event_booking.model.Event;
import com.example.event_booking.model.Role;
import com.example.event_booking.model.User;
import com.example.event_booking.repository.EventRepository;
import com.example.event_booking.repository.RoleRepository;
import com.example.event_booking.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    @Bean
    @Transactional
    public CommandLineRunner populateDatabase(RoleRepository roleRepository,
                                              UserRepository userRepository,
                                              EventRepository eventRepository) {
        return args -> {
            // Creazione dei ruoli
            if (roleRepository.count() == 0) {
                Role userRole = new Role();
                userRole.setName(Role.RoleName.ROLE_USER);
                roleRepository.save(userRole);

                Role organizerRole = new Role();
                organizerRole.setName(Role.RoleName.ROLE_ORGANIZER);
                roleRepository.save(organizerRole);
            }

            // Recupero dei ruoli
            Role userRole = roleRepository.findByName(Role.RoleName.ROLE_USER)
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setName(Role.RoleName.ROLE_USER);
                        return roleRepository.save(role);
                    });
            Role organizerRole = roleRepository.findByName(Role.RoleName.ROLE_ORGANIZER)
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setName(Role.RoleName.ROLE_ORGANIZER);
                        return roleRepository.save(role);
                    });

            // Creazione degli utenti
            if (!userRepository.existsByUsername("john_doe")) {
                User user1 = new User();
                user1.setUsername("john_doe");
                user1.setPassword(passwordEncoder.encode("password123"));
                user1.setRoles(Set.of(userRole));
                userRepository.save(user1);
            }

            if (!userRepository.existsByUsername("jane_organizer")) {
                User user2 = new User();
                user2.setUsername("jane_organizer");
                user2.setPassword(passwordEncoder.encode("securepass"));
                user2.setRoles(Set.of(organizerRole));
                userRepository.save(user2);
            }

            if (!userRepository.existsByUsername("emma_user")) {
                User user3 = new User();
                user3.setUsername("emma_user");
                user3.setPassword(passwordEncoder.encode("mypassword"));
                user3.setRoles(Set.of(userRole));
                userRepository.save(user3);
            }

            // Recupero degli utenti
            User johnDoe = userRepository.findByUsername("john_doe")
                    .orElseThrow(() -> new RuntimeException("User not found"));
            User janeOrganizer = userRepository.findByUsername("jane_organizer")
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Creazione degli eventi
            if (eventRepository.count() == 0) {
                Event event1 = new Event();
                event1.setTitle("Spring Boot Workshop");
                event1.setDescription("Learn Spring Boot from scratch");
                event1.setDate(LocalDateTime.now().plusDays(7));
                event1.setLocation("Online");
                event1.setAvailableSeats(50);
                event1.setCreator(janeOrganizer);
                eventRepository.save(event1);

                Event event2 = new Event();
                event2.setTitle("Java Advanced Course");
                event2.setDescription("Master advanced Java programming");
                event2.setDate(LocalDateTime.now().plusDays(14));
                event2.setLocation("New York City");
                event2.setAvailableSeats(30);
                event2.setCreator(janeOrganizer);
                eventRepository.save(event2);

                Event event3 = new Event();
                event3.setTitle("Database Optimization Techniques");
                event3.setDescription("Learn to optimize database queries and schemas");
                event3.setDate(LocalDateTime.now().plusDays(21));
                event3.setLocation("San Francisco");
                event3.setAvailableSeats(20);
                event3.setCreator(janeOrganizer);
                eventRepository.save(event3);


                }

        };
    }
}
