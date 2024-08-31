package ru.slavich.project.services;

import ru.slavich.project.dto.DocumentDto;
import ru.slavich.project.models.Document;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface DocumentService {

    Optional<Document> findById(long docid);

    List<DocumentDto> findAllDocument();

    List<DocumentDto> findDocumentByCatId (long catid);

    Document insert(String docname, String docauthor, String docyear, String docistochnik, String dockeyword,
                            Date docdateinput, String docannotation, long categoryId, long typeDocumentId,
                    long pathSavePdfId);

    Document updateDocument(long docid, String docname, String docauthor, String docyear, String docistochnik,
                            String dockeyword, Date docdateinput, String docannotation, long categoryId,
                            long typeDocumentId, long pathSavePdfId);

    void deleteByDocumentId(long docid);
}
