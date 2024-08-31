package ru.slavich.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.slavich.project.models.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT categories.cat_id, categories.catname_rus, categories.catname_en, categories.owner_id" +
            " FROM categories WHERE categories.owner_id is null order by categories.catname_rus asc",
            nativeQuery = true)
    List<Category> findAllParentCategory();

    @Query(value = "SELECT categories.* FROM categories WHERE categories.owner_id is not null order by " +
            "categories.catname_rus asc", nativeQuery = true)
    List<Category> findAllCategoryByOwnerId();

    @Query(value = "SELECT categories.* FROM categories WHERE categories.owner_id = :catid order by " +
            "categories.catname_rus asc", nativeQuery = true)
    List<Category> findChildById(long catid);

    @Query(value = "SELECT categories.owner_id FROM categories WHERE categories.cat_id = :catid",
            nativeQuery = true)
    Long findParentById(long catid);

    // Поиск наименований, проверяем существует ли уже такое название раздела
    Optional<Category> findByCatnamerus(String catnamerus);
}