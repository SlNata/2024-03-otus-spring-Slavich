package ru.slavich.project.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.slavich.project.dto.CategoryDto;
import ru.slavich.project.services.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DopRestController {

    private final CategoryService categoryService;

    @GetMapping("/api/catchild/{catid}")
    public List<CategoryDto> getAllCatChild(@PathVariable("catid") long catid) {
        return categoryService.findChildByCatId(catid);
    }
}
