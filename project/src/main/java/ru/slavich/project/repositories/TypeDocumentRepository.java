package ru.slavich.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.slavich.project.models.TypeDocument;

public interface TypeDocumentRepository extends JpaRepository<TypeDocument, Long> {
}
