package ru.otus.hw.processors;

import jakarta.annotation.Nonnull;
import org.springframework.batch.item.ItemProcessor;
import ru.otus.hw.models.jpa.Book;
import ru.otus.hw.models.mongo.AuthorMongo;
import ru.otus.hw.models.mongo.BookMongo;
import ru.otus.hw.models.mongo.GenreMongo;


public class BookProcessor implements ItemProcessor<Book, BookMongo> {

    @Override
    public BookMongo process(@Nonnull Book item) {

        AuthorProcessor authorProcessor = new AuthorProcessor();
        AuthorMongo author = authorProcessor.process(item.getAuthor());

        GenreProcessor genreProcessor = new GenreProcessor();
        GenreMongo genre = genreProcessor.process(item.getGenre());

        return new BookMongo(String.valueOf(item.getId()), item.getTitle(), author, genre);

    }
}
