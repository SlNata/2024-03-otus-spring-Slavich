package ru.slavich.project.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.slavich.project.dto.TypeDocumentUpdateDto;
import ru.slavich.project.services.TypeDocumentService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DisplayName("Тестирование контроллера разделов и подразделов")
@WebMvcTest(TypeDocumentController.class)
public class TypeDocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TypeDocumentController typeDocumentController;

    @MockBean
    private TypeDocumentService typeDocumentService;

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )

    @DisplayName("Должен успешно добавить тип документа")
    @Test
    public void shouldCorrectInsertTypeDocement() throws Exception {
        TypeDocumentUpdateDto typeDocumentUpdateDto = new TypeDocumentUpdateDto(0L, "Презентация");
        mockMvc.perform(get("/inserttypedocument"))
                .andExpect(status().isOk())
                .andExpect(view().name("addtypedocument"))
                .andReturn();
    }

    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )

    @DisplayName("Должен вернуть ошибку для пользователя - user при добавлении типа документа")
    @Test
    public void shouldErrorInsertTypeDocument403() throws Exception {
        mockMvc.perform(post("/inserttypedocument"))
                .andExpect(status().is4xxClientError());
    }
}
