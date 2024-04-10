package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {

        ioService.printLine("");
        List<Question> questionsList = questionDao.findAll();
        TestResult testResult = new TestResult(student);

        executeTest(questionsList, testResult);

        return testResult;
    }

    private void executeTest (List<Question> questionsList, TestResult testResult) {

        ioService.printFormattedLine("Please answer the questions below%n");

        int numberQuestion = 0;

        for (Question quest:questionsList) {
            numberQuestion = numberQuestion + 1;
            ioService.printLine(numberQuestion + ". " + quest.getQuestionCsv());

            int numberAnswer = 1;

            for (Answer answer:quest.answers()) {
                ioService.printLine("   " + numberAnswer + ") " + answer.text());
                numberAnswer++;
            }
            ioService.printLine("");
            getAnswerFromStudent(testResult, quest, quest.answers());
            ioService.printLine("");
        }
    }

    private void getAnswerFromStudent(TestResult testResult, Question question, List<Answer> answers) {
        int numberEntered = ioService.readIntForRangeWithPrompt(1, 5,
                "Choose answer:", "Invalid response number entered");
        testResult.applyAnswer(question, answers.get(numberEntered - 1).isCorrect());
    }
}
