package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе MongoDB для работы с жанрами книг")
@DataMongoTest
public class GenreRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("должен загружать жанр по id")
    @Test
    void shouldReturnCorrectGenreById() {
        Optional<Genre> optionalGenre = genreRepository.findById("1");

        assertThat(optionalGenre).isPresent();
        assertThat(optionalGenre.get().getName()).isEqualTo("Genre_1");
    }

    @DisplayName("должен загружать список всех жанров книг")
    @Test
    void shouldReturnCorrectGenresList() {
        List<Genre> listGenre = genreRepository.findAll();

        assertThat(listGenre).isNotNull().hasSize(3)
                .allMatch(s -> s.getName() != null);
    }
}
