package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с авторами книг")
@DataJpaTest
@Import({JpaAuthorRepository.class})
public class JpaAuthorRepositoryTest {

    @Autowired
    private JpaAuthorRepository jpaAuthorRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать автора по id")
    @Test
    void shouldReturnCorrectAuthorById() {
        Optional<Author> optionalAuthor = jpaAuthorRepository.findById(1L);
//        expectedAuthor - ожидается авторов
        Author expectedAuthor = em.find(Author.class, 1L);

        assertThat(optionalAuthor).isPresent()
                .get()
                .usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("должен загружать список всех авторов книг")
    @Test
    void shouldReturnCorrectAuthorsList() {
        List<Author> listAuthors = jpaAuthorRepository.findAll();

        assertThat(listAuthors).isNotNull().hasSize(3)
                .allMatch(s -> s.getFullName() != null);
    }
}