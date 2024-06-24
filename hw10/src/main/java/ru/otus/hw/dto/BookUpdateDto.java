package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw.models.Book;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookUpdateDto {
    private long id;

    private String title;

    private long authorId;

    private long genreId;

    public static BookUpdateDto toBookUpdateDto(Book book) {
        return new BookUpdateDto(book.getId(),
                book.getTitle(),
                book.getAuthor().getId(),
                book.getGenre().getId());
    }
}
