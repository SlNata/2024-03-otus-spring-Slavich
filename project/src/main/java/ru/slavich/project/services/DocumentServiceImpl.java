package ru.slavich.project.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.slavich.project.dto.DocumentDto;
import ru.slavich.project.exceptions.EntityNotFoundException;
import ru.slavich.project.models.Category;
import ru.slavich.project.models.Document;
import ru.slavich.project.models.PathSavePdf;
import ru.slavich.project.models.TypeDocument;
import ru.slavich.project.repositories.CategoryRepository;
import ru.slavich.project.repositories.DocumentRepository;
import ru.slavich.project.repositories.PathSavePdfRepository;
import ru.slavich.project.repositories.TypeDocumentRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    private final CategoryRepository categoryRepository;

    private final TypeDocumentRepository typeDocumentRepository;

    private final PathSavePdfRepository pathSavePdfRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<Document> findById (long docid) {
        return documentRepository.findById(docid);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DocumentDto> findAllDocument () {
        return documentRepository.findAll()
                .stream()
                .map(DocumentDto::toDocumentDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<DocumentDto> findDocumentByCatId (long catid) {
        return documentRepository.findDocumentsByCatId(catid)
                .stream()
                .map(DocumentDto::toDocumentDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Document insert(String docname, String docauthor, String docyear, String docistochnik, String dockeyword,
                                  Date docdateinput, String docannotation, long categoryId, long typeDocumentId,
                           long pathSavePdfId) {
        return save(0, docname, docauthor, docyear, docistochnik, dockeyword, docdateinput, docannotation,
                categoryId, typeDocumentId, pathSavePdfId);
    }

    @Transactional
    @Override
    public Document updateDocument(long docid, String docname, String docauthor, String docyear, String docistochnik,
                                   String dockeyword, Date docdateinput, String docannotation, long categoryId,
                                   long typeDocumentId, long pathSavePdfId) {
        return save(docid, docname, docauthor, docyear, docistochnik, dockeyword, docdateinput, docannotation,
                categoryId, typeDocumentId, pathSavePdfId);
    }

    @Transactional
    @Override
    public void deleteByDocumentId(long docid) {
        documentRepository.deleteById(docid);
    }

    private Document save(long docid, String docname, String docauthor, String docyear, String docistochnik,
                          String dockeyword, Date docdateinput, String docannotation, long categoryId,
                          long typeDocumentId, long pathSavePdfId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new EntityNotFoundException("Категория с таким id %d не найдена".formatted(categoryId)));

        TypeDocument typeDocument = typeDocumentRepository.findById(typeDocumentId).orElseThrow(() ->
                new EntityNotFoundException("Тип документа с таким id %d не найден".formatted(typeDocumentId)));

        PathSavePdf pathSavePdf = pathSavePdfRepository.findById(pathSavePdfId).orElseThrow(() ->
                new EntityNotFoundException("Путь с таким id %d не найден".formatted(pathSavePdfId)));

        Document document = new Document(docid, docname, docauthor, docyear, docistochnik, dockeyword, docdateinput,
                docannotation, category, typeDocument, pathSavePdf);
        return documentRepository.save(document);
    }
}
