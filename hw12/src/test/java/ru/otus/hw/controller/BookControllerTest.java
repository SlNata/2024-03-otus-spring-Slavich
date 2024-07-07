package ru.otus.hw.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.security.SecurityConfiguration;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;
import ru.otus.hw.services.GenreService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DisplayName("Тестирование контроллера книг")
@WebMvcTest({BookController.class})
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private CommentService commentService;

    @Autowired
    private BookController bookController;

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )

    @DisplayName("Должен отобразить список всех книг")
    @Test
    public void shouldReturnCorrectBookList() throws Exception {
        List<BookDto> bookList = List.of(new BookDto(1L, "new book", new Author(), new Genre()));
        given(bookService.findAll()).willReturn(bookList);
        MvcResult result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("new book"));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )

    @DisplayName("Должен пройти успешное редактирование книги")
    @Test
    public void shouldReturnCorrectUpdateBook() throws Exception {
        Book updateBook = new Book(1L, "edit book", new Author(1L, "author1"), new Genre(1L, "genre1"));
        given(bookService.findById(1)).willReturn(Optional.of(updateBook));

        MvcResult result = mockMvc.perform(get("/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editbook"))
                .andReturn();
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )

    @DisplayName("Должен успешно создать книгу")
    @Test
    public void shouldCorrectInsertBook() throws Exception {
        BookDto book = new BookDto(1L, "New Book", new Author(1L, "author1"), new Genre(1L, "genre1"));
        BookUpdateDto insertBook = new BookUpdateDto(0L,book.getTitle(), book.getAuthor().getId(), book.getGenre().getId());

        mockMvc.perform(post("/insert")
                        .with(csrf())
                        .flashAttr("updateBook", insertBook))
                .andExpect(status().is(302));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )

    @DisplayName("Должен успешно удалить книгу")
    @Test
    public void shouldCorrectDeleteBookById() throws Exception {
        mockMvc.perform(post("/delete?id=1")
                        .with(csrf()))
                .andExpect(status().is(302));
    }
}
