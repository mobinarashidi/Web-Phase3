package com.example.WebPhase3.service;

import com.example.WebPhase3.model.Question;
import com.example.WebPhase3.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository repository;

    public List<Question> getAllQuestions() {
        return repository.findAll();
    }

    public List<Question> getQuestionsByCategory(String category) {
        return repository.findByCategory(category);
    }
    public List<Question> getQuestionsByTarrahName(String tarrahName) {
        return repository.findByTarrahName(tarrahName);
    }

    public Question addQuestion(Question question) {
        return repository.save(question);
    }

      public Optional<Question> findByText(String text) {
            return repository.findByText(text);
        }
}
