package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с книгами ")
@DataJpaTest
@Import({JpaBookRepository.class, JpaGenreRepository.class})
public class JpaBookRepositoryTest {

    @Autowired
    private JpaBookRepository jpaBookRepository;

    @Autowired
    private JpaGenreRepository jpaGenreRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать книгу по id")
    @Test
    void shouldReturnCorrectBookById() {
        Optional<Book> optionalBook = jpaBookRepository.findById(1L);

        Book expectedBook = em.find(Book.class, 1L);

        assertThat(optionalBook).isPresent()
                .get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        List<Book> listBook = jpaBookRepository.findAll();

        assertThat(listBook).isNotNull().hasSize(3)
                .allMatch(s -> s.getTitle() != null)
                .allMatch(s -> s.getAuthor() != null)
                .allMatch(s -> s.getGenre() != null);
    }

    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        Author author = new Author(0, "Author_1");
        Genre genre = new Genre(0, "Genre_1");
        Book expectedBook = new Book(0, "title", author, genre);

        jpaBookRepository.save(expectedBook);
        assertThat(expectedBook.getId()).isGreaterThan(0);

        Book actualBook = em.find(Book.class, expectedBook.getId());

        assertThat(actualBook).isNotNull()
                .matches(s -> !s.getTitle().isEmpty())
                .matches(s -> s.getAuthor() != null)
                .matches(s -> s.getGenre() != null);
    }

    @DisplayName("должен сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() {
        Book initialBook = em.find(Book.class, 1L);
        Book expectedBook = new Book(1L, "title_1",
                new Author(1L, "Author_1"), new Genre(1L, "Genre_1"));

        em.detach(initialBook);

        jpaBookRepository.save(expectedBook);
        Book updatedBook = em.find(Book.class, 1L);

        assertThat(updatedBook.getTitle())
                .isNotEqualTo(initialBook.getTitle())
                .isEqualTo(expectedBook.getTitle());
    }

    @DisplayName("должен удалять книгу по id ")
    @Test
    void shouldDeleteBook() {
        Book book = em.find(Book.class, 2L);
        assertThat(book).isNotNull();
        em.detach(book);

        jpaBookRepository.deleteById(2L);

        Book deletedBook = em.find(Book.class, 2L);
        assertThat(deletedBook).isNull();

    }
}
