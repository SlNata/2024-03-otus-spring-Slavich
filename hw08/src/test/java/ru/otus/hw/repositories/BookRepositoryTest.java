package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе MongoDB для работы с книгами ")
@DataMongoTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("должен загружать книгу по id")
    @Test
    void shouldReturnCorrectBookById() {
        Optional<Book> optionalBook = bookRepository.findById("1");

        assertThat(optionalBook).isPresent();
        assertThat(optionalBook.get().getTitle()).isEqualTo("editBook_1");
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        List<Book> listBook = bookRepository.findAll();

        assertThat(listBook).isNotNull().hasSize(3);
    }

    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        Author author = new Author(null, "Author_1");
        Genre genre = new Genre(null, "Genre_1");
        Book expectedBook = new Book(null, "title", author, genre);

        Book newBook = bookRepository.save(expectedBook);
        Optional<Book> actualBook = bookRepository.findById(newBook.getId());

        assertThat(actualBook).isPresent();
        assertThat(actualBook.get().getTitle()).isEqualTo("title");
    }

    @DisplayName("должен сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() {

        Book expectedBook = new Book("1", "editBook_1",
                new Author("1", "Author1"), new Genre("1", "Genre1"));

        Book updatedBook = bookRepository.save(expectedBook);

        Optional<Book> optionalBook = bookRepository.findById(updatedBook.getId());

        assertThat(optionalBook).isPresent();
        assertThat(optionalBook.get().getTitle()).isEqualTo(expectedBook.getTitle());
    }

    @DisplayName("должен удалять книгу по id ")
    @Test
    void shouldDeleteBook() {
        Author author = new Author("1", "Author_1");
        Genre genre = new Genre("1", "Genre_1");
        Book expectedBook = new Book("2", "title", author, genre);

        bookRepository.deleteById(expectedBook.getId());

        assertThat(bookRepository.findById(expectedBook.getId())).isEmpty();
    }

    @DisplayName("не должен найти книгу с несуществующим id ")
    @Test
    void shouldNotReturnBookWithIdIsNotFind() {
        Book expectedBook = new Book("5", "editBook_1",
                new Author("1", "Author1"), new Genre("1", "Genre1"));

        assertThat(bookRepository.findById(expectedBook.getId())).isEmpty();
    }

}