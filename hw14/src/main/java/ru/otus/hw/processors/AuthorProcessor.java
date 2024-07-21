package ru.otus.hw.processors;

import jakarta.annotation.Nonnull;
import org.springframework.batch.item.ItemProcessor;
import ru.otus.hw.models.jpa.Author;
import ru.otus.hw.models.mongo.AuthorMongo;

public class AuthorProcessor implements ItemProcessor<Author, AuthorMongo> {

    @Override
    public AuthorMongo process(@Nonnull Author item) {
        return new AuthorMongo(String.valueOf(item.getId()), item.getFullName());
    }
}
