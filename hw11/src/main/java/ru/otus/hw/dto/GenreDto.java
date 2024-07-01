package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.hw.models.Genre;

@Data
@AllArgsConstructor
public class GenreDto {

    private String id;

    private String name;

    public static Genre toDomainObject(GenreDto genreDto) {
        return new Genre(genreDto.getId(), genreDto.getName());
    }

    public static GenreDto toGenreDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }
}
