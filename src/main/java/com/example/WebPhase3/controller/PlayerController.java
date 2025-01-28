package com.example.WebPhase3.controller;
import com.example.WebPhase3.model.Tarrah;
import com.example.WebPhase3.service.TarrahService;
import java.util.Optional;
import com.example.WebPhase3.model.Player;
import com.example.WebPhase3.model.Question; // اضافه کردن این خط
import com.example.WebPhase3.service.PlayerService;
import com.example.WebPhase3.service.TarrahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import com.example.WebPhase3.service.QuestionService;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;  // Specific import for ArrayList
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;  // Specific import for Collectors
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/profiles")
public class PlayerController {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private TarrahService tarrahService;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/players")
    public List<Player> getAllPlayers() {
        return playerService.findAll();
    }

    @GetMapping("/{username}")
    public Player getPlayerByUsername(@PathVariable String username) {
        return playerService.findByUsername(username);
    }

@GetMapping("/answeredQuestions/{username}")
public ResponseEntity<?> getAnsweredQuestionsWithTarrahInfo(@PathVariable String username) {
    // Find the player
    Player player = playerService.findByUsername(username);
    if (player == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player not found");
    }

    // Get player's answered questions
    List<Map<String, Object>> answeredQuestions = player.getAnsweredQuestions();
    if (answeredQuestions == null || answeredQuestions.isEmpty()) {
        return ResponseEntity.ok(new ArrayList<>()); // Creating new empty ArrayList
    }

    // Create enriched question list with tarrah information
    List<Map<String, Object>> enrichedQuestions = answeredQuestions.stream()
        .map(questionData -> {
            Map<String, Object> enrichedQuestion = new HashMap<>(questionData);

            // Get the question text
            String questionText = (String) questionData.get("text");

            // Find the original question to get tarrah information
            Optional<Question> questionOpt = questionService.findByText(questionText);

            if (questionOpt.isPresent()) {
                Question question = questionOpt.get();
                String tarrahUsername = question.getTarrahName();

                // Find tarrah information if available
                if (tarrahUsername != null && !tarrahUsername.isEmpty()) {
                    Optional<Tarrah> tarrahOpt = tarrahService.findByUsername(tarrahUsername);

                    if (tarrahOpt.isPresent()) {
                        Tarrah tarrah = tarrahOpt.get();
                        enrichedQuestion.put("tarrahName", tarrah.getUsername());
                        enrichedQuestion.put("tarrahGender", tarrah.getGender());
                    } else {
                        enrichedQuestion.put("tarrahName", tarrahUsername);
                        enrichedQuestion.put("tarrahGender", "Unknown");
                    }
                } else {
                    enrichedQuestion.put("tarrahName", "Unknown");
                    enrichedQuestion.put("tarrahGender", "Unknown");
                }
            } else {
                enrichedQuestion.put("tarrahName", "Unknown");
                enrichedQuestion.put("tarrahGender", "Unknown");
            }

            return enrichedQuestion;
        })
        .collect(Collectors.toList());

    return ResponseEntity.ok(enrichedQuestions);
}




@PutMapping("/following/{username}")
public String updateFollowing(@PathVariable String username, @RequestBody Map<String, String> requestBody) {
    String follower = requestBody.get("follower");
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
