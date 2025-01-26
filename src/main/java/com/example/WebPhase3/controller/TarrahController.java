package com.example.WebPhase3.controller;

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


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/tarrahs")
public class TarrahController {

    @Autowired
    private TarrahService tarrahService;
   @Autowired
       private PlayerService playerService;

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
    public List<Tarrah> getAllTarrahs() {
        return tarrahService.findAll();
    }

    // Get : get a Tarrah based on its username
    @GetMapping("/{username}")
    public ResponseEntity<?> getTarrahByUsername(@PathVariable String username) {
        Optional<Tarrah> tarrah = tarrahService.findByUsername(username);
        if (tarrah.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with this username not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(tarrah);
    }




    // Put : increment the Tarrah's question count
    @PutMapping("/increment/{username}")
    public ResponseEntity<String> incrementQuestionCount(@PathVariable String username) {
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
   public ResponseEntity<?> addFollower(@PathVariable String username, @RequestBody Map<String, String> requestBody) {
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


}
