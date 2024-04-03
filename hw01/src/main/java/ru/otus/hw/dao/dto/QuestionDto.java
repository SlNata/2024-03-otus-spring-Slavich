package ru.otus.hw.dao.dto;

import com.opencsv.bean.CsvBindAndSplitByPosition;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionDto {

    // определение данных по положению столбца
    @CsvBindByPosition(position = 0)
    private String text;

    /* При использовании CsvBindAndSplitByPosition одно поле в CSV-файле принимается за список данных,
    разделенных каким-либо разделителем. Входные данные разделяются по этому разделителю, а результаты
    помещаются в коллекцию и присваиваются полю bean. */
    @CsvBindAndSplitByPosition(position = 1, collectionType = ArrayList.class, elementType = Answer.class,
            converter = AnswerCsvConverter.class, splitOn = "\\|")
    private List<Answer> answers;

    public Question toDomainObject() {

        return new Question(text, answers);
    }
}
