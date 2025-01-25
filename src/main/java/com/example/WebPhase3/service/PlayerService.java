
package com.example.WebPhase3.service;

import com.example.WebPhase3.model.Player;
import com.example.WebPhase3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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


    public String updateScore(String username, Player updatedPlayer) {
          Player player = repository.findByUsername(username).orElse(null);
             if (player == null) {
                    return "Player not found";
                                 }
               player.setScore(updatedPlayer.getScore());
               player.getAnsweredQuestions().addAll(updatedPlayer.getAnsweredQuestions());
               repository.save(player);
               return player.getUsername() + " profile updated";

               }
}
