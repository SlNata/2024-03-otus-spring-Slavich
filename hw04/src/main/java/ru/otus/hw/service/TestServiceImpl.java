package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.domain.Answer;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");

        var questions = questionDao.findAll();
        TestResult testResult = new TestResult(student);

        executeTest(questions, testResult);

        return testResult;
    }

    private void executeTest(List<Question> questions, TestResult testResult) {
        ioService.printLineLocalized("TestService.answer.the.questions");

        int numberQuestion = 0;

        for (Question quest : questions) {
            numberQuestion = numberQuestion + 1;
            ioService.printLine(numberQuestion + ". " + quest.getQuestionCsv());

            int numberAnswer = 1;

            for (Answer answer : quest.answers()) {
                ioService.printLine("   " + numberAnswer + ") " + answer.text());
                numberAnswer++;
            }
            ioService.printLine("");
            getAnswerFromStudent(testResult, quest, quest.answers());
            ioService.printLine("");
        }
    }

    private void getAnswerFromStudent(TestResult testResult, Question question, List<Answer> answers) {
        int numberEntered = ioService.readIntForRangeWithPromptLocalized(1, 5,
                "TestService.choose.answer", "TestService.answer.number.correct");
        testResult.applyAnswer(question, answers.get(numberEntered - 1).isCorrect());
    }
}
