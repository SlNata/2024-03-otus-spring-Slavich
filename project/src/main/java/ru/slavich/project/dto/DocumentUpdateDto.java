package ru.slavich.project.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.slavich.project.models.Document;

import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentUpdateDto {

    private long docid;

    private String docname;

    private String docauthor;

    @Min(value = 1500, message = "Год не должен быть меньше, чем 1500")
    private String docyear;

    private String docistochnik;

    private String dockeyword;

    private Date docdateinput = Date.valueOf(LocalDate.now());

    private String docannotation;

    private long categoryId;

    private long typeDocumentId;

    private long pathSavePdfId = 1;

    public static DocumentUpdateDto toDocumentUpdateDto(Document document) {
        return new DocumentUpdateDto(document.getDocid(), document.getDocname(), document.getDocauthor(),
                document.getDocyear(), document.getDocistochnik(), document.getDockeyword(),
                document.getDocdateinput(), document.getDocannotation(), document.getCategory().getCatid(),
                document.getTypeDocument().getTypedocid(), document.getPathSavePdf().getPathid());
    }
}
