package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.hw.models.Comment;

@Data
@AllArgsConstructor
public class CommentDto {
    private String id;

    private String textComment;

    public static CommentDto toCommentDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getTextComment());
    }
}
