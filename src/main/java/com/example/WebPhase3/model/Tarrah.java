package com.example.WebPhase3.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "tarrahs")
public class Tarrah {

    @Id
    private String id;
    private String username;
    private String password;
    private List<String> followers;
    private int questionCount;
    private String gender;
    private String email;

    // Default constructor
    public Tarrah() {}

    // Parameterized constructor
    public Tarrah(String username, String password, List<String> followers, int questionCount, String gender, String email) {
        this.username = username;
        this.password = password;
        this.followers = followers;
        this.questionCount = questionCount;
        this.gender = gender;
        this.email = email;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public void setFollowers(List<String> followers) {
        this.followers = followers;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
