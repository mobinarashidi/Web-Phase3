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
}
