package ru.slavich.project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.slavich.project.models.Category;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryParentUpdateDto {
    private long catid;

    @NotBlank(message = "Поле обязательно к заполнению!")
    private String catnamerus;

    @NotBlank(message = "Поле обязательно к заполнению!")
    private String catnameen;

    private Long ownerid;

    public static CategoryParentUpdateDto toCategoryParentUpdateDto(Category category) {
        return new CategoryParentUpdateDto(category.getCatid(), category.getCatnamerus(),
                category.getCatnameen(), category.getOwnerid());
    }
}