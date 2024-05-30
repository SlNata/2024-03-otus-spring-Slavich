package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с жанрами книг")
@DataJpaTest
public class GenreRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать жанр по id")
    @Test
    void shouldReturnCorrectGenreById() {
        Optional<Genre> optionalGenre = genreRepository.findById(1L);
        Genre expectedGenre = em.find(Genre.class, 1L);

        assertThat(optionalGenre).isPresent()
                .get()
                .usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("должен загружать список всех жанров книг")
    @Test
    void shouldReturnCorrectGenresList() {
        List<Genre> listGenre = genreRepository.findAll();

        assertThat(listGenre).isNotNull().hasSize(3)
                .allMatch(s -> s.getName() != null);
    }
}
