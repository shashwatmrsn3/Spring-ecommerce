package com.home.ecommerce.Domain;

import javax.validation.constraints.NotBlank;

public class Question {
    @NotBlank(message = "empty question not allowed")
    private String question;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
