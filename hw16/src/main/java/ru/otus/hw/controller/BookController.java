package ru.otus.hw.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.services.GenreService;
import ru.otus.hw.exceptions.EntityNotFoundException;

import java.util.List;

@Controller
public class BookController {
    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final CommentService commentService;

    public BookController(BookService bookService, AuthorService authorService, GenreService genreService,
                          CommentService commentService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.commentService = commentService;
    }

    @GetMapping("/")
    public String listAllBooks(Model model) {
        List<BookDto> books = bookService.findAll();
        model.addAttribute("books",books);
        return "listbooks";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable long id, Model model) {
        Book book = bookService.findById(id).orElseThrow(EntityNotFoundException::new);
        List<Author> authors = authorService.findAll();
        List<Genre> genres = genreService.findAll();
        List<Comment> comments = commentService.findAllByBookId(id);
        model.addAttribute("updateBook", BookUpdateDto.toBookUpdateDto(book));
        model.addAttribute("authorsList",authors);
        model.addAttribute("genresList",genres);
        model.addAttribute("commentList", comments);
        return "editbook";
    }

    @PostMapping("/edit")
    public String editBook(@ModelAttribute("updateBook") BookUpdateDto bookUpdateDto,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "editbook";
        }
        Book book = bookService.update(bookUpdateDto.getId(),
                bookUpdateDto.getTitle(),
                bookUpdateDto.getAuthorId(),
                bookUpdateDto.getGenreId());
        return "redirect:/";
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

    @PostMapping("/insert")
    public String insertNewBook(@Valid @ModelAttribute("updateBook") BookUpdateDto bookUpdateDto,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            List<Author> authors = authorService.findAll();
            List<Genre> genres = genreService.findAll();
            model.addAttribute("authorsList",authors);
            model.addAttribute("genresList",genres);
            return "insertbook";
        }
        Book book = bookService.insert(bookUpdateDto.getTitle(),
                bookUpdateDto.getAuthorId(), bookUpdateDto.getGenreId());
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deletePage(@PathVariable long id, Model model) {
        model.addAttribute("book_id",id);
        return "deletebook";
    }

    @PostMapping("/delete")
    public String deleteBook(@RequestParam("id") long id) {
        bookService.deleteById(id);
        return "redirect:/";
    }
}