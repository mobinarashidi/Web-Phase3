package com.example.WebPhase3.controller;

import com.example.WebPhase3.model.Category;
import com.example.WebPhase3.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Create - Add a new category
    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        try {
            // Check if category already exists
            if (categoryService.categoryExists(category.getCategoryName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body( "!" + "دسته بندی " + category.getCategoryName() + " وجود دارد");
            }

            // Add the category
            categoryService.addCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED).body("Category added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred. Please try again!");
        }
    }


    // Read - Get all categories
    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.status(HttpStatus.OK).body(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred. Please Try again!");
        }
    }
}
