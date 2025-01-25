package com.example.WebPhase3.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "questions")
public class Question {
    @Id
    private String id;
    private boolean answered;
    private String category;
    private String text;
    private List<String> choices;
    private int correctChoice;
    private String difficulty;
    private String tarrahName;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;


    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public int getCorrectChoice() {
        return correctChoice;
    }

    public void setCorrectChoice(int correctChoice) {
        this.correctChoice = correctChoice;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getTarrahName() {
        return tarrahName;
    }

    public void setTarrahName(String tarrahName) {
        this.tarrahName = tarrahName;


    }
}
