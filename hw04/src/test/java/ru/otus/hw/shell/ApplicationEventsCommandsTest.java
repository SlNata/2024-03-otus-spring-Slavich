package ru.otus.hw.shell;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.InputProvider;
import org.springframework.shell.ResultHandlerService;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.*;

@DisplayName("Тест команд shell ")
@SpringBootTest
public class ApplicationEventsCommandsTest {
    private static final String GREETING_PATTERN = "Добро пожаловать: %s! Для запуска тестирования введите команду st или start test";
    private static final String DEFAULT_LOGIN = "AnyStudent";
    private static final String CUSTOM_LOGIN = "TestStudent";
    private static final String COMMAND_LOGIN = "login";
    private static final String COMMAND_LOGIN_SHORT = "l";
    private static final String COMMAND_TEST_SHORT = "st";
    private static final String COMMAND_LOGIN_PATTERN = "%s %s";

    private InputProvider inputProvider;

    private ArgumentCaptor<Object> argumentCaptor;

    @SpyBean
    private ResultHandlerService resultHandlerService;

    @Autowired
    private Shell shell;

    @BeforeEach
    void setUp() {
        inputProvider = mock(InputProvider.class);
        argumentCaptor = ArgumentCaptor.forClass(Object.class);
    }

    @DisplayName(" должен возвращать приветствие для всех форм команды логина")
    @Test
    void shouldReturnExpectedGreetingAfterLoginCommandEvaluated() throws Exception {
        when(inputProvider.readInput())
                .thenReturn(() -> COMMAND_LOGIN)
                .thenReturn(() -> COMMAND_LOGIN_SHORT)
                .thenReturn(() -> String.format(COMMAND_LOGIN_PATTERN, COMMAND_LOGIN_SHORT, CUSTOM_LOGIN))
                .thenReturn(null);

        shell.run(inputProvider);
        verify(resultHandlerService, times(3)).handle(argumentCaptor.capture());
        List<Object> results = argumentCaptor.getAllValues();
        assertThat(results).containsExactlyInAnyOrder(String.format(GREETING_PATTERN, DEFAULT_LOGIN),
                String.format(GREETING_PATTERN, DEFAULT_LOGIN),
                String.format(GREETING_PATTERN, CUSTOM_LOGIN));
    }

    @DisplayName(" должен возвращать CommandNotCurrentlyAvailable если при попытке выполнения команды st пользователь не авторизовался")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldReturnCommandNotCurrentlyAvailableObjectWhenUserDoesNotLoginAfterTestCommandEvaluated() {
        when(inputProvider.readInput())
                .thenReturn(() -> COMMAND_TEST_SHORT)
                .thenReturn(null);

        assertThatCode(() -> shell.run(inputProvider)).isInstanceOf(CommandNotCurrentlyAvailable.class);
    }
}
