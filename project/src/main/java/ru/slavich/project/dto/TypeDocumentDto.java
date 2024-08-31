package ru.slavich.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.slavich.project.models.TypeDocument;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeDocumentDto {

    private long typedocid;

    private String typedocname;

    public static TypeDocument toDomainObject(TypeDocumentDto typeDocumentDto) {
        return new TypeDocument(typeDocumentDto.getTypedocid(), typeDocumentDto.getTypedocname());
    }

    public static TypeDocumentDto typeDocumentDto(TypeDocument typeDocument) {
        return new TypeDocumentDto(typeDocument.getTypedocid(), typeDocument.getTypedocname());
    }
}
