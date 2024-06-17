package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.otus.hw.models.Book;

public class BookUpdateDto {
    private long id;

    @NotBlank(message = "Title is can't be empty!")
    @Size(min = 2, message = "Title must contain more then 2 chars")
    private String title;

    private long authorId;

    private long genreId;

    public BookUpdateDto() {
    }

    public BookUpdateDto(long id, String title, long authorId, long genreId) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.genreId = genreId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getGenreId() {
        return genreId;
    }

    public void setGenreId(long genreId) {
        this.genreId = genreId;
    }

    public static BookUpdateDto toBookUpdateDto(Book book) {
        return new BookUpdateDto(book.getId(),
                book.getTitle(),
                book.getAuthor().getId(),
                book.getGenre().getId());
    }
}
