package com.main.moyuquesion.model;


import java.util.Objects;

public class Quesion {
    private final String answer;

    private final String desc;

    public Quesion(String answer, String desc) {
        this.answer = answer;
        this.desc = desc;
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public int hashCode() {
        return Objects.hash(answer, desc);
    }

    public String getDesc() {
        return desc;
    }

    public String getAnswer() {
        return answer;
    }
}
