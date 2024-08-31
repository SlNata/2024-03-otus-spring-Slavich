package ru.slavich.project.services;

import ru.slavich.project.dto.TypeDocumentDto;
import ru.slavich.project.models.TypeDocument;

import java.util.List;

public interface TypeDocumentService {

    List<TypeDocumentDto> findAllTypeDocuments();

    TypeDocument insertTypeDocum(String typedocname);
}
