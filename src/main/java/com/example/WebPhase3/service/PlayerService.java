package com.example.WebPhase3.service;

import com.example.WebPhase3.model.Player;
import com.example.WebPhase3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PlayerService {
    @Autowired
    private UserRepository repository;

    public List<Player> findAll() {
        return repository.findAll();
    }

    public Player findByUsername(String username) {
        return repository.findByUsername(username).orElse(null);
    }

    public Player save(Player player) {
        return repository.save(player);
    }

    public String addFollower(String username, String newFollower) {
        Player player = findByUsername(username);
        Player followingPlayer = findByUsername(newFollower);
        if (player == null || followingPlayer == null) {
            return "Player not found";
        }
        if (player.getFollowers().contains(newFollower)) {
            return "Follower already exists";
        }
        player.getFollowers().add(newFollower);
        followingPlayer.getFollowings().add(username);
        repository.save(player);
        repository.save(followingPlayer);
        return newFollower + " added to " + username + "'s followers";
    }


    public String updateScore(String username, int newScore, String questionText, boolean isAnswerCorrect) {
        Player player = findByUsername(username);
        if (player == null) {
            throw new RuntimeException("Player not found");
        }

        // Update score
        player.setScore(newScore);

        // Update answered questions
        for (Map<String, Object> question : player.getAnsweredQuestions()) {
            if (question.get("text").equals(questionText)) {
                question.put("answered", isAnswerCorrect);
                break;
            }
        }

        // Save updated player data
        repository.save(player);

        return "Score updated successfully for " + username;
    }

}
