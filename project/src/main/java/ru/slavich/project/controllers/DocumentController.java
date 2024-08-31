package ru.slavich.project.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.slavich.project.dto.CategoryDto;
import ru.slavich.project.dto.DocumentDto;
import ru.slavich.project.dto.DocumentUpdateDto;
import ru.slavich.project.dto.TypeDocumentDto;
import ru.slavich.project.exceptions.EntityNotFoundException;
import ru.slavich.project.models.Document;
import ru.slavich.project.services.CategoryService;
import ru.slavich.project.services.DocumentService;
import ru.slavich.project.services.TypeDocumentService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
public class DocumentController {

    private final DocumentService documentService;

    private final CategoryService categoryService;

    private final TypeDocumentService typeDocumentService;

    public DocumentController(DocumentService documentService, CategoryService categoryService, TypeDocumentService
            typeDocumentService) {
        this.documentService = documentService;
        this.categoryService = categoryService;
        this.typeDocumentService = typeDocumentService;
    }

    @GetMapping("/documentsview")
    public String listAllDocument(Model model) {
        List<DocumentDto> documentList = documentService.findAllDocument();
        model.addAttribute("documents", documentList);
        List<CategoryDto> categoryList = categoryService.findAllParentCategory();
        List<CategoryDto> categoryListChild = categoryService.findAllCategoryByOwnerId();
        model.addAttribute("category_parent", categoryList);
        model.addAttribute("category_child", categoryListChild);
        return "main";
    }

    @GetMapping("/{catid}")
    public String listDocumentsByCatId(@PathVariable("catid") long catid, Model model) {
        List<DocumentDto> documentList = documentService.findDocumentByCatId(catid);
        model.addAttribute("documents", documentList);

        List<CategoryDto> categoryList = categoryService.findAllParentCategory();
        List<CategoryDto> categoryListChild = categoryService.findAllCategoryByOwnerId();
        model.addAttribute("category_parent", categoryList);
        model.addAttribute("category_child", categoryListChild);
        return "main";
    }

    @GetMapping("/insertdocument")
    public String insertDocumentPage(Model model) {
        DocumentUpdateDto documentUpdateDto = new DocumentUpdateDto(0, "", "", "",
                "", "", Date.valueOf(LocalDate.now()), "", 0L,
                0L, 1L);
        List<CategoryDto> categoryList = categoryService.findAllParentCategory();
        List<CategoryDto> categoryListChild = categoryService.findAllCategoryByOwnerId();
        List<TypeDocumentDto> typeDocumentList = typeDocumentService.findAllTypeDocuments();
        model.addAttribute("category_parent", categoryList);
        model.addAttribute("category_child", categoryListChild);
        model.addAttribute("type_doc", typeDocumentList);
        model.addAttribute("insertDocument", documentUpdateDto);
        return "adddocument";
    }

    @PostMapping("/insertdocument")
    public String insertNewDocument(@Valid @ModelAttribute("insertDocument") DocumentUpdateDto documentUpdateDto,
                                    BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            List<CategoryDto> categoryList = categoryService.findAllParentCategory();
            List<CategoryDto> categoryListChild = categoryService.findAllCategoryByOwnerId();
            List<TypeDocumentDto> typeDocumentList = typeDocumentService.findAllTypeDocuments();

            model.addAttribute("category_parent", categoryList);
            model.addAttribute("category_child", categoryListChild);
            model.addAttribute("type_doc", typeDocumentList);
            return "adddocument";
        }

        documentService.insert(documentUpdateDto.getDocname(), documentUpdateDto.getDocauthor(),
                documentUpdateDto.getDocyear(), documentUpdateDto.getDocistochnik(), documentUpdateDto.getDockeyword(),
                documentUpdateDto.getDocdateinput(), documentUpdateDto.getDocannotation(),
                documentUpdateDto.getCategoryId(), documentUpdateDto.getTypeDocumentId(),
                documentUpdateDto.getPathSavePdfId());
        return "redirect:/";
    }

    @GetMapping("/editdocument/{docid}")
    public String editDocumentPage(@PathVariable("docid") long docid, Model model) {
        Document document = documentService.findById(docid).orElseThrow(EntityNotFoundException::new);

        Long parentId = categoryService.findParentById(document.getCategory().getCatid());
        List<CategoryDto> categoryList = categoryService.findAllParentCategory();
        List<CategoryDto> categoryListChild = categoryService.findAllCategoryByOwnerId();
        List<TypeDocumentDto> typeDocumentList = typeDocumentService.findAllTypeDocuments();

        model.addAttribute("updateDocument", DocumentUpdateDto.toDocumentUpdateDto(document));
        model.addAttribute("category_parent", categoryList);
        model.addAttribute("category_child", categoryListChild);
        model.addAttribute("type_doc", typeDocumentList);
        model.addAttribute("parentId", parentId);

        return "editdocument";
    }

    @PostMapping("/editdocument")
    public String editDocument(@Valid @ModelAttribute("updateDocument") DocumentUpdateDto documentUpdateDto,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "editdocument";
        }

        documentService.updateDocument(documentUpdateDto.getDocid(), documentUpdateDto.getDocname(),
                documentUpdateDto.getDocauthor(), documentUpdateDto.getDocyear(), documentUpdateDto.getDocistochnik(),
                documentUpdateDto.getDockeyword(), documentUpdateDto.getDocdateinput(),
                documentUpdateDto.getDocannotation(), documentUpdateDto.getCategoryId(),
                documentUpdateDto.getTypeDocumentId(), documentUpdateDto.getPathSavePdfId());
        return "redirect:/";
    }

    @DeleteMapping("/deletedocument")
    public String deleteDocument(@RequestParam("docid") long docid) {
        documentService.deleteByDocumentId(docid);
        return "redirect:/";
    }
}