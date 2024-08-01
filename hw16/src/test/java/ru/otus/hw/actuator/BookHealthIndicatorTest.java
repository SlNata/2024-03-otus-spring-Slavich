package ru.otus.hw.actuator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.actuators.BookHealthIndicator;
import ru.otus.hw.repositories.BookRepository;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BookHealthIndicator.class,
        properties = { "spring.sql.init.mode=never" })
@EnableAutoConfiguration
@AutoConfigureMockMvc
@DisplayName("Тестирование")
public class BookHealthIndicatorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @Test
    @DisplayName("Отображение количества книг")
    void getBookCount() throws Exception {

        given(bookRepository.count()).willReturn(3L);

        mockMvc.perform(get("/actuator"))
                .andExpect(status().isOk());
    }
}
