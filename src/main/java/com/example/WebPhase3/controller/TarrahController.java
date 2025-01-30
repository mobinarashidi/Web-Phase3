package com.example.WebPhase3.controller;

import java.util.HashMap;

import com.example.WebPhase3.Security.JwtUtil;
import com.example.WebPhase3.model.Tarrah;
import com.example.WebPhase3.service.TarrahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.WebPhase3.model.Player;
import com.example.WebPhase3.service.PlayerService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import com.example.WebPhase3.service.QuestionService;
import com.example.WebPhase3.model.Question;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/tarrahs")
public class TarrahController {

    @Autowired
    private TarrahService tarrahService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private JwtUtil jwtUtil;
    // CRUD operations for Tarrah

    // Post : add a new Tarrah
    @PostMapping("/add")
    public ResponseEntity<String> addTarrah(@RequestBody Tarrah tarrah) {
        try {
            if (tarrahService.existsByUsername(tarrah.getUsername())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(tarrah.getUsername() + " already exists.");
            }
            if (tarrahService.existsByEmail(tarrah.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(tarrah.getEmail() + " already has an account.");
            }
            tarrahService.save(tarrah);
            return ResponseEntity.status(HttpStatus.CREATED).body(tarrah.getUsername() + " added to tarrahs successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An internal error occurred. Please Try again!");
        }
    }

    // Get : get all Tarrahs
    @GetMapping
    public List<Tarrah> getAllTarrahs(@RequestHeader("Authorization") String token) {
        if (!jwtUtil.validateToken(token.substring(7))) {
            throw new RuntimeException("Invalid JWT Token");
        }
        return tarrahService.findAll();
    }

    // Get : get a Tarrah based on its username
    @GetMapping("/{username}")
    public ResponseEntity<?> getTarrahByUsername(@PathVariable String username, @RequestHeader("Authorization") String token) {
        if (!jwtUtil.validateToken(token.substring(7))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT Token");
        }

        Optional<Tarrah> tarrah = tarrahService.findByUsername(username);
        if (tarrah.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with this username not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(tarrah);
    }

    // Put : increment the Tarrah's question count
    @PutMapping("/increment/{username}")
    public ResponseEntity<String> incrementQuestionCount(@PathVariable String username, @RequestHeader("Authorization") String token) {
        if (!jwtUtil.validateToken(token.substring(7))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT Token");
        }

        Optional<Tarrah> optionalTarrah = tarrahService.findByUsername(username);
        if (optionalTarrah.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarrah not found");
        }

        Tarrah tarrah = optionalTarrah.get();
        tarrah.setQuestionCount(tarrah.getQuestionCount() + 1);
        tarrahService.save(tarrah);
        return ResponseEntity.ok(tarrah.getUsername() + "'s question count updated");
    }


    @PutMapping("/followers/{username}")
    public ResponseEntity<?> addFollower(@PathVariable String username, @RequestBody Map<String, String> requestBody, @RequestHeader("Authorization") String token) {
        if (!jwtUtil.validateToken(token.substring(7))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT Token");
        }

        try {
            String newFollower = requestBody.get("follower");
            if (newFollower == null || newFollower.isEmpty()) {
                return ResponseEntity.badRequest().body("No follower provided in the request body.");
            }

            // Find the Tarrah by username
            Optional<Tarrah> optionalTarrah = tarrahService.findByUsername(username);
            if (optionalTarrah.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarrah not found.");
            }

            // Find the player by username
            Player player = playerService.findByUsername(newFollower);
            if (player == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player not found.");
            }

            Tarrah tarrah = optionalTarrah.get();

            // Check if the player is already a follower
            if (tarrah.getFollowers().contains(newFollower)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Follower already exists.");
            }

            // Add player to tarrah's followers
            tarrah.getFollowers().add(newFollower);

            // Add tarrah to player's followings
            player.getFollowings().add(tarrah.getUsername());

            // Save the updated entities
            tarrahService.save(tarrah);
            playerService.save(player);

            return ResponseEntity.status(HttpStatus.OK).body(newFollower + " added to " + tarrah.getUsername() + "'s followers");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    @GetMapping("/followingsQuestions/{username}")
    public ResponseEntity<?> getFollowingsQuestions(@PathVariable String username, @RequestHeader("Authorization") String token) {
        if (!jwtUtil.validateToken(token.substring(7))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT Token");
        }
        try {
            // بررسی وجود بازیکن
            Player player = playerService.findByUsername(username);
            if (player == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player not found.");
            }

            // دریافت طراحانی که بازیکن دنبال کرده است
            List<Tarrah> tarrahs = tarrahService.findTarrahsByFollowersUsername(username);
            if (tarrahs.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(username + " is not following any tarrahs.");
            }

            // استخراج داده‌ها
            List<Map<String, Object>> result = tarrahs.stream().map(tarrah -> {
                Map<String, Object> map = new HashMap<>();
                map.put("gender", tarrah.getGender());
                map.put("username", tarrah.getUsername());

                // دریافت سوالات مربوط به طراح
                List<Question> questions = questionService.getQuestionsByTarrahName(tarrah.getUsername());
                List<String> questionTexts = questions.stream()
                        .map(Question::getText)
                        .toList();
                map.put("questions", questionTexts); // به جای تعداد، متن سوالات اضافه می‌شود

                return map;
            }).toList();

            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
}
