package com.example.WebPhase3.repository;

import com.example.WebPhase3.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {
    boolean existsByCategoryName(String categoryName);

    Optional<Object> findByCategoryName(String categoryName);
}
