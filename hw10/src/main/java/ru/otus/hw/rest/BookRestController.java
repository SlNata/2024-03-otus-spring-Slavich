package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
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
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    private final CommentService commentService;

    @GetMapping("/api/books")
    public List<BookDto> listAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/api/books/{id}")
    public BookDto getBookById(@PathVariable("id") long id) {
        Book book = bookService.findById(id).orElseThrow(EntityNotFoundException::new);
        return BookDto.toBookDto(book);
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
    public void deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
    }

    @GetMapping("/api/books/{id}/comments")
    public List<CommentDto> getCommentsBookById(@PathVariable(name = "id") long id) {
        return commentService.findAllByBookId(id);
    }
}