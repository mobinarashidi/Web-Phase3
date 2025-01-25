package com.example.WebPhase3.controller;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String gender;
}
