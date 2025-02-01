package com.example.WebPhase3.controller;

import com.example.WebPhase3.Security.JwtTokenProvider;
import com.example.WebPhase3.model.Player;
import com.example.WebPhase3.model.Tarrah;
import com.example.WebPhase3.repository.UserRepository;
import com.example.WebPhase3.repository.TarrahRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserRepository playerRepository;

    @Autowired
    private TarrahRepository tarrahRepository;

    @Autowired
    private JwtTokenProvider jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String role = loginRequest.getRole();
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        logger.info("Login request received. Role: {}, Username: {}", role, username);

        if (role.equals("player")) {
            Player player = playerRepository.findByUsername(username).orElse(null);
            if (player == null) {
                logger.warn("Player not found: {}", username);
                return ResponseEntity.status(404).body("نام کاربری یافت نشد. لطفا ثبت نام کنید.");
            }
            if (!player.getPassword().equals(password)) {
                logger.warn("Invalid password for player: {}", username);
                return ResponseEntity.status(401).body("گذرواژه نادرست است!");
            }
            String token = jwtUtil.generateToken(username);
            logger.info("Player {} logged in successfully.", username);
            return ResponseEntity.ok(new JwtResponse(token));
        } else if (role.equals("tarrah")) {
            Tarrah tarrah = tarrahRepository.findByUsername(username).orElse(null);
            if (tarrah == null) {
                logger.warn("Tarrah not found: {}", username);
                return ResponseEntity.status(404).body("نام کاربری یافت نشد. لطفا ثبت نام کنید.");
            }
            if (!tarrah.getPassword().equals(password)) {
                logger.warn("Invalid password for tarrah: {}", username);
                return ResponseEntity.status(401).body("گذرواژه نادرست است!");
            }
            String token = jwtUtil.generateToken(username);
            logger.info("Tarrah {} logged in successfully.", username);
            return ResponseEntity.ok(new JwtResponse(token));
        } else {
            logger.error("Invalid role provided: {}", role);
            return ResponseEntity.status(400).body("نقش نامعتبر است.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        String role = registerRequest.getRole();
        String username = registerRequest.getUsername();
        String email = registerRequest.getEmail();

        logger.info("Register request received. Role: {}, Username: {}, Email: {}", role, username, email);

        if (role.equals("player")) {
            if (playerRepository.findByUsername(username).isPresent() ||
                    playerRepository.findByEmail(email).isPresent()) {
                logger.warn("Duplicate player registration attempt. Username: {}, Email: {}", username, email);
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
            player.setAnsweredQuestions(new LinkedList<Map<String, Object>>());

            playerRepository.save(player);
            logger.info("Player {} registered successfully.", username);
        } else if (role.equals("tarrah")) {
            if (tarrahRepository.findByUsername(username).isPresent() ||
                    tarrahRepository.findByEmail(email).isPresent()) {
                logger.warn("Duplicate tarrah registration attempt. Username: {}, Email: {}", username, email);
                return ResponseEntity.status(409).body("نام کاربری/ایمیل تکراری است. لطفا وارد شوید.");
            }
            Tarrah tarrah = new Tarrah();
            tarrah.setUsername(username);
            tarrah.setPassword(registerRequest.getPassword());
            tarrah.setEmail(email);
            tarrah.setGender(registerRequest.getGender());
            tarrah.setFollowers(new LinkedList<String>());
            tarrahRepository.save(tarrah);
            logger.info("Tarrah {} registered successfully.", username);
        } else {
            logger.error("Invalid role provided during registration: {}", role);
            return ResponseEntity.status(400).body("نقش نامعتبر است.");
        }
        String token = jwtUtil.generateToken(username);
        logger.info("Token generated for user {} during registration.", username);
        return ResponseEntity.status(201).body(new JwtResponse(token));
    }
}
