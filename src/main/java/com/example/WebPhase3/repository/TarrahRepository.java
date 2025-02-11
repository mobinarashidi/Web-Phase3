package com.example.WebPhase3.repository;
import java.util.List;
import com.example.WebPhase3.model.Tarrah;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface TarrahRepository extends MongoRepository<Tarrah, String> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<Tarrah> findByUsername(String username);
     List<Tarrah> findByFollowersContaining(String username);
    Optional<Object> findByEmail(String email);
}
