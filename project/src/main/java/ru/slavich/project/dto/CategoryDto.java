package ru.slavich.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.slavich.project.models.Category;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private long catid;

    private String catnamerus;

    private String catnameen;

    private Long ownerid;

    public Category toDomainObject() {
        return new Category(catid, catnamerus, catnameen, ownerid);
    }

    public static  CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getCatid(), category.getCatnamerus(), category.getCatnameen(),
                category.getOwnerid());
    }
}
