package ru.otus.hw.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тестирование контроллера книг")
@WebMvcTest({BookRestController.class})
public class BookRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private CommentService commentService;

    @DisplayName("Должен отобразить список всех книг")
    @Test
    public void shouldReturnCorrectBookList() throws Exception {
        List<BookDto> bookList = List.of(new BookDto(1L, "BookTitle_1", new Author(1L, "Author_1"),
                new Genre(1L, "Genre_1")));
        given(bookService.findAll()).willReturn(bookList);
        List<BookDto> expectedBook = bookList.stream().toList();
        mockMvc.perform(get("/api/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedBook)));
    }

    @DisplayName("Должен вернуть ошибку при падении сервера - ошибка 500")
    @Test
    public void shouldReturnErrorWhenDisplayBookList() throws Exception {
        given(bookService.findAll()).willThrow(RuntimeException.class);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @DisplayName("должен сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() throws Exception {

        Book book = new Book(3L, "BookTitle_1", new Author(1L, "Author_1"),
                new Genre(1L, "Genre_1"));

        given(bookService.update(book.getId(), book.getTitle(), book.getAuthor().getId(),
                book.getGenre().getId())).willReturn(book);

        BookUpdateDto updatedBook = BookUpdateDto.toBookUpdateDto(book);

        mockMvc.perform(patch("/api/books")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatedBook)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(BookDto.toBookDto(book))));
    }

    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() throws Exception {
        Book book = new Book(4L, "title", new Author(2L, "Author_2"),
                new Genre(2L, "Genre_2"));

        given(bookService.insert(book.getTitle(), book.getAuthor().getId(), book.getGenre().getId()))
                .willReturn(book);

        BookUpdateDto updateBook = BookUpdateDto.toBookUpdateDto(book);
        mockMvc.perform(post("/api/books")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateBook)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(BookDto.toBookDto(book))));
    }

    @DisplayName("Должен успешно удалить книгу")
    @Test
    public void shouldCorrectDeleteBookById() throws Exception {
        mockMvc.perform(delete("/api/books/{id}", 2L))
                .andExpect(status().isOk());
    }

    @DisplayName("Должен успешно отобразить комментарии к книге")
    @Test
    public void shouldCorrectFindCommentById() throws Exception {
        List<CommentDto> commentList = List.of(new CommentDto(2L, "Comment_2"));

        given(commentService.findAllByBookId(2L)).willReturn(commentList);

        mockMvc.perform(get("/api/books/2/comments"))
                .andExpect(status().isOk());
    }
}
