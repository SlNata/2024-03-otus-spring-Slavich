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
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис для работы с комментариями ")
@SpringBootTest(classes = CommentServiceImpl.class)
public class CommentServiceImplTest {

    @MockBean
    BookRepository bookRepository;

    @MockBean
    CommentRepository commentRepository;

    @Autowired
    CommentService commentService;

    @DisplayName("должен корректно находить комментарий по id")
    @Test
    void shouldCorrectFindCommentById() {
        Book book =  new Book(1L, "title", new Author(), new Genre());
        Comment expectedComment = new Comment(1L, "comment_1", book);
        given(commentRepository.findById(eq(1L))).willReturn(Optional.of(expectedComment));
        Optional<Comment> optionalComment = commentService.findById(1L);
        optionalComment.ifPresent(comment -> assertEquals(expectedComment, comment));
    }

    @DisplayName("должен корректно добавлять комментарий")
    @Test
    void shouldCorrectSaveComment() {
        Author author = new Author(1L, "Author_1");
        Genre genre = new Genre(1L, "Genre_1");
        Book book = new Book(1L, "title_1", author, genre);
        Comment expectedComment = new Comment(0L, "comment_2", book);

        given(bookRepository.findById(eq(1L))).willReturn(Optional.of(book));
        given(commentRepository.save(expectedComment)).willReturn(expectedComment);

        Comment actualComment = commentService.insert("comment_2", book.getId());
        assertEquals(expectedComment, actualComment);
    }

    @DisplayName("должен корректно обновлять комментарий")
    @Test
    void shouldCorrectUpdateComment() {
        Author author = new Author(1L, "Author_1");
        Genre genre = new Genre(1L, "Genre_1");
        Book book = new Book(1L, "title_1", author, genre);
        Comment expectedComment = new Comment(1L, "comment_2", book);

        given(bookRepository.findById(eq(1L))).willReturn(Optional.of(book));
        given(commentRepository.save(expectedComment)).willReturn(expectedComment);

        Comment actualComment = commentService.update(1L,"comment_2", book.getId());
        assertEquals(expectedComment, actualComment);
    }

    @DisplayName("должен корректно удалять комментарий по id")
    @Test
    void shouldCorrectDeleteById() {
        doNothing().when(commentRepository).deleteById(eq(1L));
        commentService.deleteById(1L);
    }
}
