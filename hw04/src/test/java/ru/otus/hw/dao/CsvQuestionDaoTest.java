package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("Тест класса CsvQuestionDao")
@SpringBootTest(classes = CsvQuestionDao.class)
public class CsvQuestionDaoTest {
    private static final String CSV_PATH = "questions-test.csv";
    @MockBean
    private TestFileNameProvider testFileNameProvider;
    private List<Question> questionList;

    @Autowired
    private CsvQuestionDao csvQuestionDao;

    @BeforeEach
    public void setUp() {
        Mockito.when(testFileNameProvider.getTestFileName()).thenReturn(CSV_PATH);

        List<Answer> listAnswerForQuestion = new ArrayList<>();
        listAnswerForQuestion.add(new Answer("Yes", true));
        listAnswerForQuestion.add(new Answer("No", false));

        List<Answer> listAnswerForSecondQuestion = new ArrayList<>();
        listAnswerForSecondQuestion.add(new Answer("Yes", false));
        listAnswerForSecondQuestion.add(new Answer("No", true));

        questionList = new ArrayList<>();
        questionList.add(new Question("In school, Albert Einstein failed most of the subjects, except for physics and math?", listAnswerForQuestion));
        questionList.add(new Question("The Beatles is a famous rock band from Manchester, the United Kingdom?", listAnswerForSecondQuestion));
    }

    @DisplayName("Корректное получение вопросов")
    @Test
    void shouldBeCorrectGetQuestions() {
        List<Question> resultQuestionList = csvQuestionDao.findAll();
        assertEquals(questionList.size(), resultQuestionList.size());
        assertThat(resultQuestionList).isEqualTo(questionList);
    }
}
