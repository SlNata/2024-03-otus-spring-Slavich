package ru.slavich.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.slavich.project.controllers.rest.DopRestController;
import ru.slavich.project.dto.CategoryDto;
import ru.slavich.project.services.CategoryService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тестирование контроллера книг")
@WebMvcTest({DopRestController.class})
public class DopRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CategoryService categoryService;

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )

    @DisplayName("Должен успешно отобразить раздел/подраздел по id")
    @Test
    public void shouldCorrectFindCategoryChildById() throws Exception {
        List<CategoryDto> categoryDtoParentList = List.of(new CategoryDto(1L, "Журналы",
                "Magazine", null));

        given(categoryService.findChildByCatId(1L)).willReturn(categoryDtoParentList);

        mockMvc.perform(get("/api/catchild/1"))
                .andExpect(status().isOk());
    }
}