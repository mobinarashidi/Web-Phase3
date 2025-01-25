package com.example.WebPhase3.controller;

import com.example.WebPhase3.model.Player;
import com.example.WebPhase3.model.Question; // اضافه کردن این خط
import com.example.WebPhase3.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Question> getAnsweredQuestions(@PathVariable String username) {
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
    public String updateScore(@PathVariable String username, @RequestBody Player updatedPlayer) {
    return playerService.updateScore(username, updatedPlayer);
     }
}