package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw.security.LoginContext;
import ru.otus.hw.service.TestRunnerService;

@ShellComponent(value = "Application Events Commands")
@RequiredArgsConstructor
public class ApplicationEventsCommands {

    private final LoginContext loginContext;

    private final TestRunnerService testRunnerService;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "AnyStudent") String userName) {
        loginContext.login(userName);
        return String.format("Добро пожаловать: %s! " +
                "Для запуска тестирования введите команду st или start test", userName);
    }

    @ShellMethod(value = "Start test", key = {"st", "start test"})
    @ShellMethodAvailability(value = "isTestCommandAvailable")
    public void runTest() {
        testRunnerService.run();
    }

    private Availability isTestCommandAvailable() {
        return loginContext.isUserLoggedIn()
                ? Availability.available()
                : Availability.unavailable("Сначала залогиньтесь! Для этого введите команду l или login.");
    }
}