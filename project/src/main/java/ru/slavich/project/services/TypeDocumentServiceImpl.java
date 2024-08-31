package ru.slavich.project.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.slavich.project.dto.TypeDocumentDto;
import ru.slavich.project.models.TypeDocument;
import ru.slavich.project.repositories.TypeDocumentRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TypeDocumentServiceImpl implements TypeDocumentService {

    private final TypeDocumentRepository typeDocumentRepository;

    @Transactional(readOnly = true)
    @Override
    public List<TypeDocumentDto> findAllTypeDocuments () {
        return typeDocumentRepository.findAll()
                .stream()
                .map(TypeDocumentDto::typeDocumentDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public TypeDocument insertTypeDocum(String typedocname) {
        return save(0, typedocname);
    }

    private TypeDocument save(long typedocid, String typedocname) {
        TypeDocument typeDocument = new TypeDocument(typedocid, typedocname);
        return typeDocumentRepository.save(typeDocument);
    }
}
