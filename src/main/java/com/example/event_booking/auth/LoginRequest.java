package com.example.event_booking.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}