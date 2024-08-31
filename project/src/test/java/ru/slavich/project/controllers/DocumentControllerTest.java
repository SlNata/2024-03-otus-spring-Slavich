package ru.slavich.project.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.slavich.project.dto.CategoryDto;
import ru.slavich.project.dto.DocumentDto;
import ru.slavich.project.dto.DocumentUpdateDto;
import ru.slavich.project.models.Category;
import ru.slavich.project.models.PathSavePdf;
import ru.slavich.project.models.TypeDocument;
import ru.slavich.project.services.CategoryService;
import ru.slavich.project.services.DocumentService;
import ru.slavich.project.services.TypeDocumentService;

import java.sql.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тестирование контроллера разделов и подразделов")
@WebMvcTest(DocumentController.class)
public class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DocumentController documentController;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private DocumentService documentService;

    @MockBean
    private TypeDocumentService typeDocumentService;

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )

    @DisplayName("Должен отобразить список всех документов")
    @Test
    public void shouldReturnCorrectDocumentList() throws Exception {

        List<DocumentDto> documentList = List.of(new DocumentDto(1L, "Spring Security",
                "Tom Mathew", "2024", "java.pdf",null, null,
                null,  new Category(1L, "Журналы","Public", 1L),
                new TypeDocument(1L, "Журналы"), new PathSavePdf(1L, "D:\\pdf")));
        given(documentService.findAllDocument()).willReturn(documentList);

        List<CategoryDto> categoryDtoParentList = List.of(new CategoryDto(1L, "Журналы", "Magazine", null));
        List<CategoryDto> categoryDtoChildList = List.of(new CategoryDto(2L, "Публицистические", "Public", 1L));
        given(categoryService.findAllParentCategory()).willReturn(categoryDtoParentList);
        given(categoryService.findAllCategoryByOwnerId()).willReturn(categoryDtoChildList);

        MvcResult result = mockMvc.perform(get("/documentsview"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )

    @DisplayName("Должен отобразить список документов по id раздела/подраздела")
    @Test
    public void shouldReturnCorrectDocumentListByCategoryId() throws Exception {

        List<DocumentDto> documentList = List.of(new DocumentDto(1L, "Spring Security",
                "Tom Mathew", "2024", "java.pdf",null, null,
                null,  new Category(1L, "Журналы","Public", 1L),
                new TypeDocument(1L, "Журналы"), new PathSavePdf(1L, "D:\\pdf")));
        given(documentService.findDocumentByCatId(1L)).willReturn(documentList);

        List<CategoryDto> categoryDtoParentList = List.of(new CategoryDto(1L, "Журналы", "Magazine", null));
        List<CategoryDto> categoryDtoChildList = List.of(new CategoryDto(2L, "Публицистические", "Public", 1L));
        given(categoryService.findAllParentCategory()).willReturn(categoryDtoParentList);
        given(categoryService.findAllCategoryByOwnerId()).willReturn(categoryDtoChildList);

        MvcResult result = mockMvc.perform(get("/1"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )

    @DisplayName("Должен успешно создать карточку книги")
    @Test
    public void shouldCorrectInsertDocument() throws Exception {
        DocumentDto documentDto = new DocumentDto(1L, "Spring Security",
                "Tom Mathew", "2024", "java.pdf",null, Date.valueOf("2024-06-03"),
                null,  new Category(1L, "Журналы","Public", 1L),
                new TypeDocument(1L, "Журналы"), new PathSavePdf(1L, "D:\\pdf"));

        DocumentUpdateDto documentUpdateDto = new DocumentUpdateDto(0L, documentDto.getDocname(), documentDto.getDocauthor(),
                documentDto.getDocyear(), documentDto.getDocistochnik(), documentDto.getDockeyword(), documentDto.getDocdateinput(),
                documentDto.getDocannotation(), documentDto.getCategory().getCatid(), documentDto.getTypeDocument().getTypedocid(),
                documentDto.getPathSavePdf().getPathid());
        mockMvc.perform(post("/insertdocument")
                        .with(csrf())
                        .flashAttr("insertDocument", documentUpdateDto))
                .andExpect(status().is(302));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_User"}
    )

    @DisplayName("Должен вернуть ошибку для пользователя - user при добавлении книги")
    @Test
    public void shouldErrorInsertDocument() throws Exception {
        mockMvc.perform(post("/insertdocument"))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )

    @DisplayName("Должен успешно удалить карточку книги")
    @Test
    public void shouldCorrectDeleteDocumentById() throws Exception {
        mockMvc.perform(delete("/deletedocument").param("docid", String.valueOf(2))
                        .with(csrf()))
                .andExpect(redirectedUrl("/"));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )

    @DisplayName("Должен вернуть ошибку для пользователя - user при удалении книги")
    @Test
    public void shouldDeleteCategoryChildById403() throws Exception {
        mockMvc.perform(delete("/deletedocument").param("docid", String.valueOf(2)))
                .andExpect(status().is4xxClientError());
    }
}
