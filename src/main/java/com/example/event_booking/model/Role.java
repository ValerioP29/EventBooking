package com.example.event_booking.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false, unique = true)
    private RoleName name;

    public enum RoleName {
        ROLE_USER,
        ROLE_ORGANIZER
    }
}
