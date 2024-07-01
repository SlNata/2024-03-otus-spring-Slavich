package ru.otus.hw.rest;

import com.github.cloudyrock.spring.v5.EnableMongock;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

@RestController
@RequiredArgsConstructor
@EnableMongock
public class BookRestController {

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    @GetMapping("/api/books")
    public Flux<BookDto> listAllBooks() {
        return bookRepository.findAll().map(BookDto::toBookDto);
    }

    @GetMapping("/api/books/{id}")
    public Mono<ResponseEntity<BookDto>> getBookById(@PathVariable("id") String id) {
        return bookRepository.findById(id).map(BookDto::toBookDto).map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));
    }

    @PutMapping("/api/books/{id}")
    public Mono<BookDto> updateBookById(@PathVariable("id") String id, @RequestBody BookUpdateDto bookUpdateDto) {
        return update(bookUpdateDto);
    }

    @PostMapping("/api/books")
    public Mono<BookDto> insertNewBook(@RequestBody BookUpdateDto bookUpdateDto) {
        return insert(bookUpdateDto);
    }

    @GetMapping("/api/authors")
    public Flux<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().map(AuthorDto::toAuthorDto);
    }

    @GetMapping("/api/genres")
    public Flux<GenreDto> getAllGenres() {
        return genreRepository.findAll().map(GenreDto::toGenreDto);
    }

    @DeleteMapping("/api/books/{id}")
    public Mono<Void> deleteBook(@PathVariable("id") String id) {
       return bookRepository.deleteById(id);
    }

    @GetMapping("/api/books/{id}/comments")
    public Flux<Comment> getCommentsBookById(@PathVariable String id) {
        return commentRepository.findAllByBookId(id);
    }

    private Mono<BookDto> update(BookUpdateDto bookUpdateDto) {
        return save(bookUpdateDto.getId(), bookUpdateDto.getTitle(),
                bookUpdateDto.getAuthorId(), bookUpdateDto.getGenreId());
    }

    private Mono<BookDto> insert(BookUpdateDto bookUpdateDto) {
        return save(null, bookUpdateDto.getTitle(), bookUpdateDto.getAuthorId(), bookUpdateDto.getGenreId());
    }

    private Mono<BookDto> save(String id, String title, String authorId, String genreId) {
        var genresMono = genreRepository.findById(genreId);
        var authorMono = authorRepository.findById(authorId);
        return Mono.zip(genresMono, authorMono, (genre, author) -> new Book(id, title, author, genre))
                .flatMap(bookRepository::save)
                .map(BookDto::toBookDto);
    }
}