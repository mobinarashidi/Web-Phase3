package com.example.WebPhase3.controller;

import com.example.WebPhase3.model.Player;
import com.example.WebPhase3.model.Question; // اضافه کردن این خط
import com.example.WebPhase3.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/profiles")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @GetMapping("/players")
    public List<Player> getAllPlayers() {
        return playerService.findAll();
    }

    @GetMapping("/{username}")
    public Player getPlayerByUsername(@PathVariable String username) {
        return playerService.findByUsername(username);
    }

    @GetMapping("/answeredQuestions/{username}")
    public List<Map<String, Object>> getAnsweredQuestions(@PathVariable String username) {
        Player player = playerService.findByUsername(username);
        if (player == null) {
            throw new RuntimeException("Player not found");
        }
        return player.getAnsweredQuestions();
    }

    @PutMapping("/following/{username}")
    public String updateFollowing(@PathVariable String username, @RequestBody String follower) {
    return playerService.addFollower(username, follower);
    }

    @PutMapping("/updateScore/{username}")
    public ResponseEntity<?> updateScore(
            @PathVariable String username,
            @RequestBody Map<String, Object> requestBody) {
        try {
            // Extract data from requestBody
            int newScore = (int) requestBody.get("score");
            String text = (String) requestBody.get("text");
            boolean answer = (boolean) requestBody.get("answer");

            // Delegate the update logic to the service layer
            String result = playerService.updateScore(username, newScore, text, answer);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // Handle any exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the score.");
        }
    }
}
