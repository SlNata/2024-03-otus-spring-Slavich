package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw.models.Book;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookUpdateDto {
    private long id;

    @NotBlank(message = "Title is can't be empty!")
    @Size(min = 2, message = "Title must contain more then 2 chars")
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
