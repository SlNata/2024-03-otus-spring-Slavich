package ru.slavich.project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.slavich.project.models.TypeDocument;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeDocumentUpdateDto {

    private long typedocid;

    @NotBlank(message = "Поле обязательно к заполнению!")
    private String typedocname;

    public static TypeDocumentUpdateDto toTypeDocumentUpdateDto (TypeDocument typeDocument) {
        return new TypeDocumentUpdateDto(typeDocument.getTypedocid(), typeDocument.getTypedocname());
    }
}
