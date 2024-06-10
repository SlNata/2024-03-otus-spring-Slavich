package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе MongoDB для работы с комментариями к книгам ")
@DataMongoTest
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("должен загружать комментарий по id")
    @Test
    void shouldReturnCorrectCommentById() {
        Optional<Comment> optionalComment = commentRepository.findById("1");

        assertThat(optionalComment).isPresent();
        assertThat(optionalComment.get().getTextComment()).isEqualTo("text_1");
    }

    @DisplayName("должен загружать список всех комментариев к книге")
    @Test
    void shouldReturnCorrectCommentsList() {
        List<Comment> listComment = commentRepository.findAllByBookId("1");

        assertThat(listComment).isNotNull().hasSize(2)
                .allMatch(s -> s.getTextComment() != null);
    }

    @DisplayName("должен сохранять новый комментарий")
    @Test
    void shouldSaveNewComment() {
        Author author = new Author("1", "Author_1");
        Genre genre = new Genre("1", "Genre_1");
        Book book = new Book("1", "title", author, genre);

        Comment expectedComment = new Comment(null, "comment_2", book);

        Comment newComment = commentRepository.save(expectedComment);

        Optional<Comment> optionalComment = commentRepository.findById(newComment.getId());

        assertThat(optionalComment).isNotNull();
        assertThat(optionalComment.get().getTextComment()).isEqualTo("comment_2");
    }

    @DisplayName("должен удалять комментарий по id ")
    @Test
    void shouldDeleteComment() {

        Author author = new Author("1", "Author_1");
        Genre genre = new Genre("1", "Genre_1");
        Book book = new Book("1", "title", author, genre);

        Comment expectedComment = new Comment("3", "comment_2", book);

        commentRepository.deleteById(expectedComment.getId());

        assertThat(commentRepository.findById(expectedComment.getId())).isEmpty();
    }
}