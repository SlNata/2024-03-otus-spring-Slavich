package ru.otus.hw.domain;

import java.util.List;

public record Question(String text, List<Answer> answers) {

    //Разделение вопроса от вариантов ответа и возвращение строки с вопросом
    public String getQuestionCsv() {

        return text.split(";")[0];
    }
}
