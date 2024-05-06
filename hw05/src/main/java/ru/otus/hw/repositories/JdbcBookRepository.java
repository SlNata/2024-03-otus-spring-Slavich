package ru.otus.hw.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcBookRepository implements BookRepository {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public JdbcBookRepository(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Optional<Book> findById(long id) {
        List<Book> bookList = namedParameterJdbcOperations.query("SELECT books.id as id, books.title as title," +
                " books.author_id as author_id, books.genre_id as genre_id, authors.full_name as author_name," +
                " genres.name as genre_name FROM books INNER JOIN authors ON books.author_id = authors.id" +
                " INNER JOIN genres ON books.genre_id = genres.id" +
                " WHERE books.id = :id", Map.of("id", id), new BookRowMapper());
        if (bookList.size() == 1) {
            return Optional.of(bookList.get(0));
        }

        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        return namedParameterJdbcOperations.query("SELECT books.id as id, books.title as title," +
                " books.author_id as author_id, books.genre_id as genre_id, authors.full_name as author_name," +
                " genres.name as genre_name FROM books INNER JOIN authors ON books.author_id = authors.id" +
                " INNER JOIN genres ON books.genre_id = genres.id", new BookRowMapper());
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        namedParameterJdbcOperations.update(
                "DELETE FROM books WHERE id = :id", Map.of("id", id));
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("title", book.getTitle());
        parameterSource.addValue("author_id", book.getAuthor().getId());
        parameterSource.addValue("genre_id", book.getGenre().getId());

        namedParameterJdbcOperations.update("INSERT INTO books (title, author_id, genre_id) " +
                "VALUES (:title, :author_id, :genre_id)", parameterSource, keyHolder, new String[]{"id"});

        book.setId(keyHolder.getKeyAs(Long.class));
        return book;
    }

    private Book update(Book book) {
        int res = namedParameterJdbcOperations.update(
                "UPDATE books SET title = :title, author_id = :author_id, genre_id = :genre_id WHERE id = :id"
                , Map.of("id", book.getId(), "title", book.getTitle(),
                        "author_id", book.getAuthor().getId(), "genre_id", book.getGenre().getId()));
        if (res <= 0) {
            throw new EntityNotFoundException(String.format("Book with id = %s not found", book.getId()));
        }
        return book;
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String title = rs.getString("title");
            Author author = new Author(rs.getLong("author_id"), rs.getString("author_name"));
            Genre genre = new Genre(rs.getLong("genre_id"), rs.getString("genre_name"));
            return new Book(id, title, author, genre);
        }
    }
}
