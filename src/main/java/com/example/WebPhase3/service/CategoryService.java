package com.example.WebPhase3.service;

import com.example.WebPhase3.model.Category;
import com.example.WebPhase3.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Add a new category
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    // Get all categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Check if a category exists by name
    public boolean categoryExists(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName).isPresent();
    }
}
