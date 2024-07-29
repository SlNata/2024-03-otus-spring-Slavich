package ru.otus.hw.processors;

import jakarta.annotation.Nonnull;
import org.springframework.batch.item.ItemProcessor;
import ru.otus.hw.models.jpa.Genre;
import ru.otus.hw.models.mongo.GenreMongo;

public class GenreProcessor implements ItemProcessor<Genre, GenreMongo> {

    @Override
    public GenreMongo process(@Nonnull Genre item) {
        return new GenreMongo(String.valueOf(item.getId()), item.getName());
    }
}
