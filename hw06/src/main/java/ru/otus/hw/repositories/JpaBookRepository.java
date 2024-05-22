package ru.otus.hw.repositories;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Repository
@AllArgsConstructor
public class JpaBookRepository implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Book> findById(long id) {
        EntityGraph<?> graph = em.getEntityGraph("book-graph");
        Map<String, Object> properties = Map.of(FETCH.getKey(), graph);
        return Optional.ofNullable(em.find(Book.class, id, properties));
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("SELECT b FROM Book b LEFT JOIN fetch b.author " +
                "LEFT JOIN fetch b.genre", Book.class).getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
        }
        return em.merge(book);
    }

    @Override
    public void deleteById(long id) {
        Book book = em.find(Book.class, id);
        em.remove(book);
    }
}
