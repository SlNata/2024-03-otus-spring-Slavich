package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис для работы с книгами ")
@SpringBootTest(classes = BookServiceImpl.class)
public class BookServiceImplTest {

    @MockBean
    BookRepository bookRepository;

    @MockBean
    AuthorRepository authorRepository;

    @MockBean
    GenreRepository genreRepository;

    @Autowired
    BookService bookService;

    @DisplayName("должен корректно находить книгу по id")
    @Test
    void shouldCorrectFindBookById() {
        Book expectedBook = new Book(1L, "title", new Author(), new Genre());
        given(bookRepository.findById(eq(1L))).willReturn(Optional.of(expectedBook));
        Optional<Book> optionalBook = bookService.findById(1L);
        optionalBook.ifPresent(book -> assertEquals(expectedBook, book));
    }

    @DisplayName("должен корректно находить все книги")
    @Test
    void shouldCorrectFindAllBooks() {
        given(bookRepository.findAll()).willReturn(List.of(
                new Book(1L, "Book_1", new Author(), new Genre()),
                new Book(1L, "Book_2", new Author(), new Genre()),
                new Book(1L, "Book_3", new Author(), new Genre())
        ));
        List<Book> actualBooks = bookService.findAll();
        assertEquals(actualBooks, List.of(
                new Book(1L, "Book_1", new Author(), new Genre()),
                new Book(1L, "Book_2", new Author(), new Genre()),
                new Book(1L, "Book_3", new Author(), new Genre())
        ));
    }

    @DisplayName("должен корректно добавлять книгу")
    @Test
    void shouldCorrectSaveBook() {
        Author author = new Author(1L, "Author_1");
        Genre genre = new Genre(1L, "Genre_1");
        Book expectedBook = new Book(0L, "title_1", author, genre);
        given(authorRepository.findById(eq(1L))).willReturn(Optional.of(author));
        given(genreRepository.findById(eq(1L))).willReturn(Optional.of(genre));
        given(bookRepository.save(expectedBook)).willReturn(expectedBook);
        Book actualBook = bookService.insert("title_1", 1L, 1L);
        assertEquals(expectedBook, actualBook);
    }

    @DisplayName("должен корректно обновлять книгу")
    @Test
    void shouldCorrectUpdateBook() {
        Author author = new Author(1L, "Author_1");
        Genre genre = new Genre(1L, "Genre_1");
        Book expectedBook = new Book(1L, "title_2", author, genre);
        given(authorRepository.findById(eq(1L))).willReturn(Optional.of(author));
        given(genreRepository.findById(eq(1L))).willReturn(Optional.of(genre));
        given(bookRepository.save(expectedBook)).willReturn(expectedBook);
        Book actualBook = bookService.update(1L, "title_2", 1L, 1L);
        assertEquals(expectedBook, actualBook);
    }

    @DisplayName("должен корректно удалять книгу по id")
    @Test
    void shouldCorrectDeleteById() {
        doNothing().when(bookRepository).deleteById(eq(1L));
        bookService.deleteById(1L);
    }
}
