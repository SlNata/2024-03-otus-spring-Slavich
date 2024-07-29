package ru.otus.hw.repositories.jpa;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.jpa.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(attributePaths = {"author", "genre"})
    Optional<Book> findById(@Nonnull Long id);

    @Override
    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findAll();
}