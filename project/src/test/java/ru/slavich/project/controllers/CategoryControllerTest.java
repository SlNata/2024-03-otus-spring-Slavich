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
import ru.slavich.project.dto.CategoryParentUpdateDto;
import ru.slavich.project.exceptions.CategoryValidator;
import ru.slavich.project.services.CategoryService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тестирование контроллера разделов и подразделов")
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryController categoryController;

    @MockBean
    private CategoryValidator categoryValidator;

    @MockBean
    private CategoryService categoryService;

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )

    @DisplayName("Должен отобразить список всех разделов и подразделов")
    @Test
    public void shouldReturnCorrectCategoryList() throws Exception {
        List<CategoryDto> categoryDtoParentList = List.of(new CategoryDto(1L, "Журналы", "Magazine", null));
        List<CategoryDto> categoryDtoChildList = List.of(new CategoryDto(2L, "Публицистические", "Public", 1L));
        given(categoryService.findAllParentCategory()).willReturn(categoryDtoParentList);
        given(categoryService.findAllCategoryByOwnerId()).willReturn(categoryDtoChildList);
        MvcResult result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertTrue(content.contains("Журналы"));
    }

    @DisplayName("Должен вернуть ошибку для неавторизованного пользователя")
    @Test
    void ShouldReturnErrorForWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )

    @DisplayName("Должен успешно удалить раздел")
    @Test
    public void shouldCorrectDeleteCategoryParentById() throws Exception {
        mockMvc.perform(delete("/deleteparent").param("catid", String.valueOf(1))
                        .with(csrf()))
                .andExpect(redirectedUrl("/"));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )

    @DisplayName("Должен вернуть ошибку для пользователя - user при удалении раздела")
    @Test
    public void shouldErrorDeleteCategoryParentById403() throws Exception {
        mockMvc.perform(delete("/deleteparent").param("catid", String.valueOf(1)))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )

    @DisplayName("Должен успешно удалить подраздел")
    @Test
    public void shouldCorrectDeleteCategoryChildById() throws Exception {
        mockMvc.perform(delete("/deletechild").param("catid", String.valueOf(2))
                        .with(csrf()))
                .andExpect(redirectedUrl("/"));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )

    @DisplayName("Должен вернуть ошибку для пользователя - user при удалении подраздела")
    @Test
    public void shouldDeleteCategoryChildById403() throws Exception {
        mockMvc.perform(delete("/deletechild").param("catid", String.valueOf(2)))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )

    @DisplayName("Должен успешно добавить раздел")
    @Test
    public void shouldCorrectInsertCategoryParent() throws Exception {
        CategoryDto categoryDto = new CategoryDto(3L, "Журналы", "Magazine", null);
        CategoryParentUpdateDto categoryParentUpdateDto = new CategoryParentUpdateDto(0L, categoryDto.getCatnamerus(),
                categoryDto.getCatnamerus(), categoryDto.getOwnerid());

        mockMvc.perform(post("/insertcategoryparent")
                        .with(csrf())
                        .flashAttr("insertCategoryParent", categoryParentUpdateDto))
                .andExpect(status().is(302));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )

    @DisplayName("Должен вернуть ошибку для пользователя - user при добавлении раздела")
    @Test
    public void shouldErrorInsertCategorParent() throws Exception {
        mockMvc.perform(post("/insertcategoryparent"))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )

    @DisplayName("Должен успешно добавить подраздел")
    @Test
    public void shouldCorrectInsertCategoryChild() throws Exception {
        CategoryDto categoryDto = new CategoryDto(4L, "Журналы", "Magazine", 3L);
        CategoryParentUpdateDto categoryParentUpdateDto = new CategoryParentUpdateDto(0L, categoryDto.getCatnamerus(),
                categoryDto.getCatnamerus(), categoryDto.getOwnerid());

        mockMvc.perform(post("/insertcategorychild")
                        .with(csrf())
                        .flashAttr("insertCategoryChild", categoryParentUpdateDto))
                .andExpect(status().is(302));
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )

    @DisplayName("Должен вернуть ошибку для пользователя - user при добавлении раздела")
    @Test
    public void shouldErrorInsertCategoryChild() throws Exception {
        mockMvc.perform(post("/insertcategorychild"))
                .andExpect(status().is4xxClientError());
    }

}