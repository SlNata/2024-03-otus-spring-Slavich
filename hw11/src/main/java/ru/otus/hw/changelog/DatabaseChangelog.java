package ru.otus.hw.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

@ChangeLog
public class DatabaseChangelog {
    private Author firstAuthor;

    private Author secondAuthor;

    private Author thirdAuthor;

    private Genre firstGenre;

    private Genre secondGenre;

    private Genre thirdGenre;

    private Book firstBook;

    private Book secondBook;

    private Book thirdBook;

    private Comment firstComment;

    private Comment secondComment;

    private Comment thirdComment;

    @ChangeSet(order = "001", id = "dropDataBase", author = "slavich", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "initAuthors", author = "slavich", runAlways = true)
    public void initAuthors(AuthorRepository authorRepository) {
        firstAuthor = authorRepository.save(new Author("1", "Author_1")).block();
        secondAuthor = authorRepository.save(new Author("2", "Author_2")).block();
        thirdAuthor = authorRepository.save(new Author("3", "Author_3")).block();
    }

    @ChangeSet(order = "003", id = "initGenres", author = "slavich", runAlways = true)
    public void initGenres(GenreRepository genreRepository) {
        firstGenre = genreRepository.save(new Genre("1", "Genre_1")).block();
        secondGenre = genreRepository.save(new Genre("2", "Genre_2")).block();
        thirdGenre = genreRepository.save(new Genre("3", "Genre_3")).block();
    }

    @ChangeSet(order = "004", id = "initBooks", author = "slavich", runAlways = true)
    public void initBooks(BookRepository bookRepository) {
        firstBook = bookRepository.save(new Book("1", "Book_1", firstAuthor, firstGenre)).block();
        secondBook = bookRepository.save(new Book("2", "Book_2", secondAuthor, secondGenre)).block();
        thirdBook = bookRepository.save(new Book("3", "Book_3", thirdAuthor, thirdGenre)).block();
    }

    @ChangeSet(order = "005", id = "initComments", author = "slavich", runAlways = true)
    public void initComments(CommentRepository commentRepository) {
        firstComment = commentRepository.save(new Comment("1", "text_1", firstBook)).block();
        secondComment = commentRepository.save(new Comment("2", "text_2", secondBook)).block();
        thirdComment = commentRepository.save(new Comment("3", "text_3", firstBook)).block();
    }
}
