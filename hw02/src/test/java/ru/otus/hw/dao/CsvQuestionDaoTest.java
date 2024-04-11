package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Тест класса CsvQuestionDao")
public class CsvQuestionDaoTest {
    private static final String CSV_PATH = "questions-test.csv";
    private TestFileNameProvider testFileNameProvider;
    private List<Question> questionList;

    @BeforeEach
    public void setUp() {
        testFileNameProvider = Mockito.mock(AppProperties.class);
        Mockito.when(testFileNameProvider.getTestFileName()).thenReturn(CSV_PATH);

        List<Answer> listAnswerForQuestion = new ArrayList<>();
        listAnswerForQuestion.add(new Answer("Yes", true));
        listAnswerForQuestion.add(new Answer("No", false));

        questionList = new ArrayList<>();
        questionList.add(new Question("In school, Albert Einstein failed most of the subjects, except for physics and math?", listAnswerForQuestion));
        questionList.add(new Question("question 2", listAnswerForQuestion));
    }

    @DisplayName("Корректное получение вопросов")
    @Test
    void shouldBeCorrectGetQuestions() {
        CsvQuestionDao csvQuestionDao = new CsvQuestionDao(testFileNameProvider);
        List<Question> resultQuestionList = csvQuestionDao.findAll();
        assertEquals(questionList.size(), resultQuestionList.size());
        assertThat(resultQuestionList).isEqualTo(questionList);
    }
}
