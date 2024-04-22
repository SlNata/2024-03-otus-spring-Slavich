package ru.otus.hw.domain;

import java.util.List;

public record Question(String text, List<Answer> answers) {
    public String getQuestionCsv() {

        return text.split(";")[0];
    }
}
