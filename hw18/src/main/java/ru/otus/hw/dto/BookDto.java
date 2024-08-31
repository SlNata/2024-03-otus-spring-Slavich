package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

@Data
@AllArgsConstructor
public class BookDto {
    private Long id;

    private String title;

    private Author author;

    private Genre genre;

    public static Book toDomainObject(BookDto bookDto) {
        return new Book(bookDto.getId(), bookDto.getTitle(), bookDto.getAuthor(), bookDto.getGenre());
    }

    public static BookDto toBookDto(Book book) {
        return new BookDto(book.getId(),book.getTitle(),book.getAuthor(),book.getGenre());
    }
}
