package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;

import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        //Получение вопросов из Dao
        List<Question> questionsList = questionDao.findAll();
        //ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");

        int numberQuestion = 0;
        // Вывод вопросов с вариантами ответов
        for (Question quest:questionsList) {
            numberQuestion = numberQuestion + 1;
            ioService.printLine(numberQuestion + ". " + quest.getQuestionCsv());

            for (Answer answer:quest.answers()) {
                ioService.printLine("  " + "- " + answer.text());
            }
            ioService.printLine("");
        }
    }
}
