package com.example.WebPhase3.controller;

import com.example.WebPhase3.Security.JwtUtil;
import com.example.WebPhase3.model.Question;
import com.example.WebPhase3.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private JwtUtil jwtUtil;

    // Get all questions
    @GetMapping
    public List<Question> getAllQuestions(@RequestHeader("Authorization") String token) {
        if (!jwtUtil.validateToken(token.substring(7))) {
            throw new RuntimeException("Invalid JWT Token");
        }
        return questionService.getAllQuestions();
    }

    // Get questions by category
    @GetMapping("/{category}")
    public ResponseEntity<?> getQuestionsByCategory(@PathVariable String category, @RequestHeader("Authorization") String token) {
        if (!jwtUtil.validateToken(token.substring(7))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT Token");
        }
        if (questionService.getQuestionsByCategory(category).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No question found for this category!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(questionService.getQuestionsByCategory(category));
    }

    // Get a random question
    @GetMapping("/random")
    public ResponseEntity<?> getRandomQuestion(@RequestHeader("Authorization") String token) {
        if (!jwtUtil.validateToken(token.substring(7))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT Token");
        }
        if (questionService.getAllQuestions().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("no questions found!"); // No questions available
        }
        List<Question> allQuestions = questionService.getAllQuestions();
        int randomIndex = (int) (Math.random() * allQuestions.size());
        Question randomQuestion = allQuestions.get(randomIndex);
        return ResponseEntity.ok(randomQuestion);
    }

    // Get questions by tarrah name
    @GetMapping("/tarrah/{tarrahName}")
    public ResponseEntity<?> getQuestionsByTarrah(@PathVariable String tarrahName, @RequestHeader("Authorization") String token) {
        if (!jwtUtil.validateToken(token.substring(7))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT Token");
        }
        if (questionService.getQuestionsByTarrahName(tarrahName).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No question found from " + tarrahName);
        }
        return ResponseEntity.status(HttpStatus.OK).body(questionService.getQuestionsByTarrahName(tarrahName));
    }

    // Add a new question
    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question, @RequestHeader("Authorization") String token) {
        if (!jwtUtil.validateToken(token.substring(7))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT Token");
        }
        try {
            questionService.addQuestion(question);
            return ResponseEntity.status(HttpStatus.OK).body("Question added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred. Please try again!");
        }
    }


}
