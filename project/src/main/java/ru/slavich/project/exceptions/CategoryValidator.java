package ru.slavich.project.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.slavich.project.dto.CategoryParentUpdateDto;
import ru.slavich.project.services.CategoryService;

@Component
public class CategoryValidator implements Validator {

    private final CategoryService categoryService;

    @Autowired
    public CategoryValidator(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return CategoryParentUpdateDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        CategoryParentUpdateDto categoryParentUpdateDto = (CategoryParentUpdateDto) o;

        if (categoryService.findByCategoryNameRus(categoryParentUpdateDto.getCatnamerus()).isPresent()) {
            errors.rejectValue("catnamerus", "", "Раздел с таким именем уже существует");
        }
    }
}