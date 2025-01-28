package com.example.WebPhase3.repository;

import com.example.WebPhase3.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findByCategory(String category);
    List<Question> findByTarrahName(String tarrahName);
    Optional<Question> findByText(String text);
}
