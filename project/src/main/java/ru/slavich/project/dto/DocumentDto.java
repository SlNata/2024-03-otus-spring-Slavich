package ru.slavich.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.slavich.project.models.Category;
import ru.slavich.project.models.Document;
import ru.slavich.project.models.PathSavePdf;
import ru.slavich.project.models.TypeDocument;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDto {

    private long docid;

    private String docname;

    private String docauthor;

    private String docyear;

    private String docistochnik;

    private String dockeyword;

    private Date docdateinput;

    private String docannotation;

    private Category category;

    private TypeDocument typeDocument;

    private PathSavePdf pathSavePdf;

    public static Document toDomainObject(DocumentDto documentDto) {
        return new Document(documentDto.getDocid(), documentDto.getDocname(), documentDto.getDocauthor(),
                documentDto.getDocyear(), documentDto.getDocistochnik(), documentDto.getDockeyword(),
                documentDto.getDocdateinput(), documentDto.getDocannotation(), documentDto.getCategory(),
                documentDto.getTypeDocument(), documentDto.getPathSavePdf());
    }

    public static DocumentDto toDocumentDto(Document document) {
        return new DocumentDto(document.getDocid(), document.getDocname(), document.getDocauthor(),
                document.getDocyear(), document.getDocistochnik(), document.getDockeyword(),
                document.getDocdateinput(), document.getDocannotation(), document.getCategory(),
                document.getTypeDocument(), document.getPathSavePdf());
    }

}
