package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями к книгам ")
@DataJpaTest
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать комментарий по id")
    @Test
    void shouldReturnCorrectCommentById() {
        Optional<Comment> optionalComment = commentRepository.findById(1L);
        Comment expectedComment = em.find(Comment.class, 1L);

        assertThat(optionalComment).isPresent()
                .get()
                .usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("должен загружать список всех комментариев к книге")
    @Test
    void shouldReturnCorrectCommentsList() {
        List<Comment> listComment = commentRepository.findAllByBookId(3L);

        assertThat(listComment).isNotNull().hasSize(2)
                .allMatch(s -> s.getTextComment() != null);
    }

    @DisplayName("должен сохранять новый комментарий")
    @Test
    void shouldSaveNewComment() {
        Author author = new Author(1L, "Author_1");
        Genre genre = new Genre(1L, "Genre_1");
        Book book = new Book(1L, "title", author, genre);

        Comment expectedComment = new Comment(0, "comment_2", book);

        commentRepository.save(expectedComment);
        assertThat(expectedComment.getId()).isGreaterThan(0);

        Comment actualComment = em.find(Comment.class, expectedComment.getId());
        assertThat(actualComment).isNotNull()
                .matches(s -> !s.getTextComment().isEmpty())
                .matches(s -> s.getBook().getId() > 0);
    }

    @DisplayName("должен сохранять измененный комментарий")
    @Test
    void shouldSaveUpdatedComment() {

        Comment initialComment = em.find(Comment.class, 1L);
        Book book = new Book(1L, "title_1",
                new Author(1L, "Author_1"), new Genre(1L, "Genre_1"));

        Comment expectedComment = new Comment(1L, "comment_1", book);
        em.detach(initialComment);

        commentRepository.save(expectedComment);
        Comment updatedComment = em.find(Comment.class, 1L);

        assertThat(updatedComment.getTextComment())
                .isNotEqualTo(initialComment.getTextComment())
                .isEqualTo(expectedComment.getTextComment());
    }

    @DisplayName("должен удалять комментарий по id ")
    @Test
    void shouldDeleteComment() {
        Comment comment = em.find(Comment.class, 2L);
        assertThat(comment).isNotNull();
        em.detach(comment);

        commentRepository.deleteById(2L);
        Comment deletedComment = em.find(Comment.class, 2L);

        assertThat(deletedComment).isNull();
    }
}
