package ru.otus.hw.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.models.mongo.BookMongo;

import java.util.Optional;

public interface MongoBookRepository extends MongoRepository<BookMongo, String> {

    Optional<BookMongo> findById(String id);
}