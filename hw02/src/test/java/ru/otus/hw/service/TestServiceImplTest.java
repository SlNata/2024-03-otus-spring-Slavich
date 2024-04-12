package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис тестирования")
@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {

    @Mock
    private QuestionDao questionDao;
    @Mock
    private IOService ioService;

    @BeforeEach
    public void setUp() {
        Mockito.when(ioService.readIntForRangeWithPrompt(1, 5,
                "Choose answer:", "Invalid response number entered")).thenReturn(2);
        List<Answer> listAnswerForQuestion = new ArrayList<>();
        listAnswerForQuestion.add(new Answer("Yes", true));
        listAnswerForQuestion.add(new Answer("No", false));

        List<Answer> listAnswerForSecondQuestion = new ArrayList<>();
        listAnswerForSecondQuestion.add(new Answer("Yes", false));
        listAnswerForSecondQuestion.add(new Answer("No", true));

        List<Question> questionList = new ArrayList<>();
        questionList.add(new Question("In school, Albert Einstein failed most of the subjects, except for physics and math?", listAnswerForQuestion));
        questionList.add(new Question("The Beatles is a famous rock band from Manchester, the United Kingdom?", listAnswerForSecondQuestion));

        Mockito.when(questionDao.findAll()).thenReturn(questionList);
    }

    @DisplayName("Корректный запуск тестирования")
    @Test
    void shouldBeCorrectExecuteTestFor() {
        TestService testService = new TestServiceImpl(ioService, questionDao);

        Student student = new Student("firstname", "lastname");
        TestResult testResult = testService.executeTestFor(student);

        assertThat(testResult.getStudent()).isNotNull().isEqualTo(student);
        assertThat(testResult.getAnsweredQuestions().size()).isEqualTo(2);
    }
}
