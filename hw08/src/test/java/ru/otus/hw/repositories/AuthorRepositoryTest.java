package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе MongoDB для работы с авторами книг")
@DataMongoTest
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("должен загружать автора по id")
    @Test
    void shouldReturnCorrectAuthorById() {
        Optional<Author> optionalAuthor = authorRepository.findById("1");

        assertThat(optionalAuthor).isPresent();
        assertThat(optionalAuthor.get().getFullName()).isEqualTo("Author_1");
    }

    @DisplayName("должен загружать список всех авторов книг")
    @Test
    void shouldReturnCorrectAuthorsList() {
        List<Author> listAuthors = authorRepository.findAll();

        assertThat(listAuthors).isNotNull().hasSize(3)
                .allMatch(s -> s.getFullName() != null);
    }
}