package ru.otus.hw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;
import ru.otus.hw.exceptions.EntityNotFoundException;

import java.util.List;

@Controller
public class BookController {
    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    public BookController(BookService bookService, AuthorService authorService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @GetMapping("/")
    public String listAllBooks(Model model) {
        return "listbooks";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable long id, Model model) {
        Book book = bookService.findById(id).orElseThrow(EntityNotFoundException::new);
        List<Author> authors = authorService.findAll();
        List<Genre> genres = genreService.findAll();
        model.addAttribute("updateBook", BookUpdateDto.toBookUpdateDto(book));
        model.addAttribute("authorsList",authors);
        model.addAttribute("genresList",genres);
        return "editbook";
    }

    @GetMapping("/insert")
    public String insertPage(Model model) {
        BookUpdateDto updateBook = new BookUpdateDto(0, "New title",0,0);
        List<Author> authors = authorService.findAll();
        List<Genre> genres = genreService.findAll();
        model.addAttribute("updateBook",updateBook);
        model.addAttribute("authorsList",authors);
        model.addAttribute("genresList",genres);
        return "insertbook";
    }

    @GetMapping("/delete/{id}")
    public String deletePage(@PathVariable long id, Model model) {
        model.addAttribute("book_id",id);
        return "deletebook";
    }
}