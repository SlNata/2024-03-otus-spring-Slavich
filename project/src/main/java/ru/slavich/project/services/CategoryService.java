package ru.slavich.project.services;

import ru.slavich.project.dto.CategoryDto;
import ru.slavich.project.models.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryDto> findAllParentCategory();

    List<CategoryDto> findAllCategoryByOwnerId();

    Category insertCategoryParent(String catnamerus, String catnameen, Long ownerid);

    Category insertCategoryChild(String catnamerus, String catnameen, Long ownerid);

    void deleteByCatId(long catid);

    List<CategoryDto> findChildByCatId(long catid);

    Long findParentById(long catid);

    Optional<Category> findByCategoryNameRus(String catnamerus);
}
