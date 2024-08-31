package ru.slavich.project.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doc_id")
    private long docid;

    @Column(name = "doc_name")
    private String docname;

    @Column(name = "doc_author")
    private String docauthor;

    @Column(name = "doc_year")
    private String docyear;

    @Column(name = "doc_istochnik")
    private String docistochnik;

    @Column(name = "doc_keyword", nullable = true)
    private String dockeyword;

    @Column(name = "doc_dateinput")
    private Date docdateinput;

    @Column(name = "annotation", nullable = true)
    private String docannotation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "typedoc_id")
    private TypeDocument typeDocument;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "path_id")
    private PathSavePdf pathSavePdf;
}
