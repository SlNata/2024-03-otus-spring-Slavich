package ru.otus.hw.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnswerTest {

    @Test
    void shouldHaveCorrectConstructor() {
        Answer answer1 = new Answer("test", true);
        assertAll("answer",
                () -> assertEquals("test", answer1.text()),
                () -> assertTrue(answer1.isCorrect()));
    }
}