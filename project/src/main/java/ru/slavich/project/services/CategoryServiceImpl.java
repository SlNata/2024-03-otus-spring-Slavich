package ru.slavich.project.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.slavich.project.dto.CategoryDto;
import ru.slavich.project.models.Category;
import ru.slavich.project.repositories.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDto> findAllParentCategory() {
        return categoryRepository.findAllParentCategory()
                .stream().map(CategoryDto::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDto> findAllCategoryByOwnerId() {
        return categoryRepository.findAllCategoryByOwnerId()
                .stream()
                .map(CategoryDto::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Category insertCategoryParent(String catnamerus, String catnameen, Long ownerid) {
        return save(0, catnamerus, catnameen, null);
    }

    @Transactional
    @Override
    public Category insertCategoryChild(String catnamerus, String catnameen, Long ownerid) {
        return save(0, catnamerus, catnameen, ownerid);
    }

    @Transactional
    @Override
    public void deleteByCatId(long catid) {
        categoryRepository.deleteById(catid);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDto> findChildByCatId(long catid) {
        return categoryRepository.findChildById(catid)
                .stream()
                .map(CategoryDto::toCategoryDto)
                .collect(Collectors.toList());
    }

    private Category save(long catid, String catnamerus, String catnameen, Long ownerid) {
        Category category = new Category(catid, catnamerus, catnameen, ownerid);
        return categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    @Override
    public Long findParentById(long catid) {
        return categoryRepository.findParentById(catid);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Category> findByCategoryNameRus(String catnamerus) {
        return categoryRepository.findByCatnamerus(catnamerus);
    }
}