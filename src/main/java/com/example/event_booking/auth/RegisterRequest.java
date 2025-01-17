package com.example.event_booking.auth;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
}