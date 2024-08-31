package ru.slavich.project.controllers;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.slavich.project.dto.CategoryDto;
import ru.slavich.project.dto.CategoryParentUpdateDto;
import ru.slavich.project.exceptions.CategoryValidator;
import ru.slavich.project.services.CategoryService;

import java.util.List;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    private final CategoryValidator categoryValidator;

    public CategoryController(CategoryService categoryService, CategoryValidator categoryValidator) {
        this.categoryService = categoryService;
        this.categoryValidator = categoryValidator;
    }

    @GetMapping("/")
    public String listAllCategory(Model model) {
        List<CategoryDto> categoryList = categoryService.findAllParentCategory();
        List<CategoryDto> categoryListChild = categoryService.findAllCategoryByOwnerId();
        model.addAttribute("category_parent", categoryList);
        model.addAttribute("category_child", categoryListChild);
        return "main";
    }

    @DeleteMapping("/deletechild")
    public String deleteCategoryChild(@RequestParam("catid") long catid) {
        categoryService.deleteByCatId(catid);
        return "redirect:/";
    }

    @DeleteMapping("/deleteparent")
    public String deleteCategoryParent(@RequestParam("catid") long catid) {
        categoryService.deleteByCatId(catid);
        return "redirect:/";
    }

    @GetMapping("/insertcategoryparent")
    public String insertCategoryPage(Model model) {
        CategoryParentUpdateDto categoryParentUpdateDto = new CategoryParentUpdateDto(0, "",
                "", null);
        model.addAttribute("insertCategoryParent", categoryParentUpdateDto);
        return "addcategory";
    }

    @PostMapping("/insertcategoryparent")
    public String insertNewCategory(@Valid @ModelAttribute("insertCategoryParent") CategoryParentUpdateDto
                                                categoryParentUpdateDto, BindingResult bindingResult) {
        categoryValidator.validate(categoryParentUpdateDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "addcategory";
        }
        categoryService.insertCategoryParent(categoryParentUpdateDto.getCatnamerus(),
                categoryParentUpdateDto.getCatnameen(),null);
        return "redirect:/";
    }

    @GetMapping("/insertcategorychild")
    public String insertCategoryChildPage(Model model) {
        CategoryParentUpdateDto categoryParentUpdateDto = new CategoryParentUpdateDto(0, "",
                "", 0L);
        List<CategoryDto> categoryDtoList = categoryService.findAllParentCategory();
        model.addAttribute("category_parent", categoryDtoList);
        model.addAttribute("insertCategoryChild", categoryParentUpdateDto);
        return "addcategorychild";
    }

    @PostMapping("/insertcategorychild")
    public String insertNewCategoryChild(@Valid @ModelAttribute("insertCategoryChild") CategoryParentUpdateDto
                                         categoryParentUpdateDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<CategoryDto> categoryDtoList = categoryService.findAllParentCategory();
            model.addAttribute("category_parent", categoryDtoList);
            return "addcategorychild";
        }
        categoryService.insertCategoryChild(categoryParentUpdateDto.getCatnamerus(),
                categoryParentUpdateDto.getCatnameen(),
                categoryParentUpdateDto.getOwnerid());
        return "redirect:/";
    }

}