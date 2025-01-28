package com.example.WebPhase3.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "players")
public class Player {
    @Id
    private String id;
    private String gender;
    private String username;
    private String password;
    private String email;
    private String bio;
    private int challenges;
    private int score;
    private List<String> followers;
    private List<String> followings;
    private List<Map<String, Object>> answeredQuestions;

    // getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getChallenges() {
        return challenges;
    }

    public void setChallenges(int challenges) {
        this.challenges = challenges;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public void setFollowers(List<String> followers) {
        this.followers = followers;
    }

    public List<String> getFollowings() {
        return followings;
    }

    public void setFollowings(List<String> followings) {
        this.followings = followings;
    }

    public List<Map<String, Object>> getAnsweredQuestions() {
        return answeredQuestions;
    }

    public void setAnsweredQuestions(List<Map<String, Object>> answeredQuestions) {
        this.answeredQuestions = answeredQuestions;
    }

    public void updateAnsweredQuestions(String text, Boolean answeredCorrect) {
        Map<String, Object> newQuestion = new HashMap<>();

        newQuestion.put("text", text);
        newQuestion.put("answered", answeredCorrect);

        answeredQuestions.add(newQuestion);
    }

}

