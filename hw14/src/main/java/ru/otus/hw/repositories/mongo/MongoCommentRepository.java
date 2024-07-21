package ru.otus.hw.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.models.mongo.CommentMongo;

import java.util.List;

public interface MongoCommentRepository extends MongoRepository<CommentMongo, String> {

    List<CommentMongo> findAllByBookId(String bookId);
}
