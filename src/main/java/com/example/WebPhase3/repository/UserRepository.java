package com.example.WebPhase3.repository;

import com.example.WebPhase3.model.Player; // تغییر مسیر اینجا
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<Player, String> {
    Player findByUsername(String username);
    Optional<Player> findByEmail(String email);
}
