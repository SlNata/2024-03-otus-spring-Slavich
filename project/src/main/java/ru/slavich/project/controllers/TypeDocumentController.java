package ru.slavich.project.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.slavich.project.dto.TypeDocumentUpdateDto;
import ru.slavich.project.services.TypeDocumentService;

@Controller
public class TypeDocumentController {

    private final TypeDocumentService typeDocumentService;

    public TypeDocumentController(TypeDocumentService typeDocumentService) {
        this.typeDocumentService = typeDocumentService;
    }

    @GetMapping("/inserttypedocument")
    public String insertTypeDocPage(Model model) {
        TypeDocumentUpdateDto typeDocumentUpdateDto = new TypeDocumentUpdateDto(0, "");
        model.addAttribute("insertTypeDocument", typeDocumentUpdateDto);
        return "addtypedocument";
    }

    @PostMapping("/inserttypedocument")
    public String insertTypeDoc(@Valid @ModelAttribute("insertTypeDocument") TypeDocumentUpdateDto typeDocumentUpdateDto,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addtypedocument";
        }
        typeDocumentService.insertTypeDocum(typeDocumentUpdateDto.getTypedocname());
        return "redirect:/";
    }

}
