package com.example.a15151.entitys;

import java.util.List;

public class Question  {
    private final String questionText;
    private final List<String> answerOptions;
    private final int correctAnswerIndex;

    public Question(String questionText, List<String> answerOptions, int correctAnswerIndex) {
        this.questionText = questionText;
        this.answerOptions = answerOptions;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getAnswerOptions() {
        return answerOptions;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}

