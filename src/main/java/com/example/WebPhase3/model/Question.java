package com.example.WebPhase3.model;

import lombok.Data;

@Data
public class Question { // اضافه کردن کلمه کلیدی public
    private String text;
    private boolean answered;

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
    }
}
