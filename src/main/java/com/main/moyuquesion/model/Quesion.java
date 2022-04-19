package com.main.moyuquesion.model;


import java.util.Objects;

public class Quesion {

    private final String answer;

    private final String description;

    public Quesion(String answer, String description) {
        this.answer = answer;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public int hashCode() {
        return Objects.hash(answer, description);
    }

}
