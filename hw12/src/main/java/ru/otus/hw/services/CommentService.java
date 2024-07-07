package ru.otus.hw.services;

import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<Comment> findAllByBookId(long bookId);

    Optional<Comment> findById(long id);

    Comment insert(String textComment, long bookId);

    Comment update(long id, String textComment);

    void deleteById(long id);
}
