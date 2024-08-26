package ru.otus.hw.rest;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Book;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    private final CommentService commentService;

    @CircuitBreaker(name = "bookBreaker", fallbackMethod = "unknownBookListFallback")
    @GetMapping("/api/books")
    public List<BookDto> listAllBooks() {
        return bookService.findAll();
    }

    @CircuitBreaker(name = "bookBreaker", fallbackMethod = "unknownBookFallback")
    @GetMapping("/api/books/{id}")
    public BookDto getBookById(@PathVariable(value = "id", required = false) Long id) {
        return bookService.findById(id).orElse(null);
    }

    @PatchMapping("/api/books")
    public BookDto updateBookById(@RequestBody BookUpdateDto bookUpdateDto) {
            Book book = bookService.update(bookUpdateDto.getId(),
                    bookUpdateDto.getTitle(),
                    bookUpdateDto.getAuthorId(),
                    bookUpdateDto.getGenreId());
            return BookDto.toBookDto(book);
    }

    @PostMapping("/api/books")
    public BookDto insertNewBook(@RequestBody BookUpdateDto bookUpdateDto) {
        Book book = bookService.insert(bookUpdateDto.getTitle(),
                bookUpdateDto.getAuthorId(), bookUpdateDto.getGenreId());
        return BookDto.toBookDto(book);
    }

    @DeleteMapping("/api/books/{id}")
    public void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteById(id);
    }

    @CircuitBreaker(name = "commentBreaker", fallbackMethod = "unknownCommentFallback")
    @GetMapping("/api/books/{id}/comments")
    public List<CommentDto> getCommentsBookById(@PathVariable(name = "id") Long id) {
        return commentService.findAllByBookId(id);
    }

    public BookDto unknownBookFallback(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new BookDto(null, "NaN", null, null);
    }

    public List<BookDto> unknownBookListFallback(Exception ex) {
        log.error("List book error:" + ex.getMessage(), ex);
        return new ArrayList<>();
    }

    public List<CommentDto> unknownCommentListFallback(Exception ex) {
        log.error("List book error:" + ex.getMessage(), ex);
        return new ArrayList<>();
    }

}