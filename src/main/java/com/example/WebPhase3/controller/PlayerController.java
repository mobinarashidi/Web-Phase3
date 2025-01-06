package com.example.WebPhase3.controller;

import com.example.WebPhase3.model.Player;
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

    @PutMapping("/players/followings/{name}")
    public Player followPlayer(@PathVariable String name, @RequestBody String following) {
        Player player = playerService.findByUsername(name);
        if (player != null && !player.getFollowings().contains(following)) {
            player.getFollowings().add(following);
            Player followingPlayer = playerService.findByUsername(following);
            if (followingPlayer != null && !followingPlayer.getFollowers().contains(name)) {
                followingPlayer.getFollowers().add(name);
                playerService.save(followingPlayer);
            }
            return playerService.save(player);
        }
        return null;
    }
}
