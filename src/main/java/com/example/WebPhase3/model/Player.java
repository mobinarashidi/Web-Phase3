package com.example.WebPhase3.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "players")
public class Player {
    @Id
    private String id;
    private String username;
    private String bio;
    private int challenges;
    private int score;
    private List<String> followers;
    private List<String> followings;
    private List<Question> answeredQuestions;

    public List<String> getFollowers() {
        return followers;
    }

    public List<String> getFollowings() {
        return followings;
    }
}

@Data
class Question {
    private String text;
    private boolean answered;
}
