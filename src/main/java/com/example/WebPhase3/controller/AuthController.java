package com.example.WebPhase3.controller;

import com.example.WebPhase3.model.Player;
import com.example.WebPhase3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        Player player = userRepository.findByUsername(loginRequest.getUsername()).orElse(null);
        if (player == null) {
            return ResponseEntity.status(404).body("نام کاربری یافت نشد. لطفا ثبت نام کنید.");
        }
        if (!player.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(401).body("گذرواژه نادرست است!");
        }
        return ResponseEntity.ok("ورود موفقیت آمیز بود!");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent() ||
            userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            return ResponseEntity.status(409).body("نام کاربری/ایمیل تکراری است. لطفا وارد شوید.");
        }

        Player player = new Player();
        player.setUsername(registerRequest.getUsername());
        player.setPassword(registerRequest.getPassword());
        player.setEmail(registerRequest.getEmail());
        player.setGender(registerRequest.getGender());
        player.setBio("سلام! منم یکی از بازیکن‌های سوال پیچ هستم، خوشحال میشم باهم رقابت کنیم.");

        userRepository.save(player);

        return ResponseEntity.status(201).body("ثبت نام موفقیت آمیز بود!");
    }
}
