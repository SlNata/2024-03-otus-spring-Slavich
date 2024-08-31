package ru.slavich.project.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id")
    private long catid;

    @Column(name = "catname_rus")
    private String catnamerus;

    @Column(name = "catname_en")
    private String catnameen;

    //т.к. ownerid может быть равен NULL, то в таком случае следует писать тип данных Long - ссылочный тип данных
    @Column(name = "owner_id", nullable = true)
    private Long ownerid;

}