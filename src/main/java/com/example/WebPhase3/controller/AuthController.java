package com.example.WebPhase3.controller;

import com.example.WebPhase3.model.Player;
import com.example.WebPhase3.model.Question;
import com.example.WebPhase3.model.Tarrah;
import com.example.WebPhase3.repository.UserRepository;
import com.example.WebPhase3.repository.TarrahRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.LinkedList;
import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository playerRepository;

    @Autowired
    private TarrahRepository tarrahRepository;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String role = loginRequest.getRole();
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        if (role.equals("player")) {
            Player player = playerRepository.findByUsername(username).orElse(null);
            if (player == null) {
                return ResponseEntity.status(404).body("نام کاربری یافت نشد. لطفا ثبت نام کنید.");
            }
            if (!player.getPassword().equals(password)) {
                return ResponseEntity.status(401).body("گذرواژه نادرست است!");
            }
            return ResponseEntity.ok("ورود موفقیت آمیز بود!");
        } else if (role.equals("tarrah")) {
            Tarrah tarrah = tarrahRepository.findByUsername(username).orElse(null);
            if (tarrah == null) {
                return ResponseEntity.status(404).body("نام کاربری یافت نشد. لطفا ثبت نام کنید.");
            }
            if (!tarrah.getPassword().equals(password)) {
                return ResponseEntity.status(401).body("گذرواژه نادرست است!");
            }
            return ResponseEntity.ok("ورود موفقیت آمیز بود!");
        } else {
            return ResponseEntity.status(400).body("نقش نامعتبر است.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        String role = registerRequest.getRole();
        String username = registerRequest.getUsername();
        String email = registerRequest.getEmail();

        if (role.equals("player")) {
            if (playerRepository.findByUsername(username).isPresent() ||
                    playerRepository.findByEmail(email).isPresent()) {
                return ResponseEntity.status(409).body("نام کاربری/ایمیل تکراری است. لطفا وارد شوید.");
            }
            Player player = new Player();
            player.setUsername(username);
            player.setPassword(registerRequest.getPassword());
            player.setEmail(email);
            player.setGender(registerRequest.getGender());
            player.setBio("سلام! منم یکی از بازیکن‌های سوال پیچ هستم، خوشحال میشم باهم رقابت کنیم.");
            player.setFollowers(new LinkedList<String>());
            player.setFollowings(new LinkedList<String>());
            player.setAnsweredQuestions(new LinkedList<Question>());


            playerRepository.save(player);
        } else if (role.equals("tarrah")) {
            if (tarrahRepository.findByUsername(username).isPresent() ||
                    tarrahRepository.findByEmail(email).isPresent()) {
                return ResponseEntity.status(409).body("نام کاربری/ایمیل تکراری است. لطفا وارد شوید.");
            }
            Tarrah tarrah = new Tarrah();
            tarrah.setUsername(username);
            tarrah.setPassword(registerRequest.getPassword());
            tarrah.setEmail(email);
            tarrah.setGender(registerRequest.getGender());
            tarrah.setFollowers(new LinkedList<String>());
            tarrahRepository.save(tarrah);
        } else {
            return ResponseEntity.status(400).body("نقش نامعتبر است.");
        }

        return ResponseEntity.status(201).body("ثبت نام موفقیت آمیز بود!");
    }
}