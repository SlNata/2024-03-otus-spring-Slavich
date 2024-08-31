package ru.slavich.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;
import ru.slavich.project.models.Document;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query(value = "SELECT documents.doc_id, documents.doc_name, documents.doc_author, documents.doc_year, " +
            "documents.doc_istochnik, documents.doc_keyword, documents.doc_dateinput, documents.annotation, " +
            "documents.cat_id, documents.typedoc_id, documents.path_id FROM documents INNER JOIN categories ON" +
            " categories.cat_id = documents.cat_id INNER JOIN typedocuments ON typedocuments.typedoc_id = " +
            "documents.typedoc_id WHERE categories.cat_id = :catid  OR categories.owner_id = :catid" +
            " order by typedocuments.typedoc_name, documents.doc_name asc", nativeQuery = true)
        List<Document> findDocumentsByCatId(@RequestParam("catid") long catid);
}