package ru.otus.hw.commandlinerunners;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.hw.service.TestRunnerService;

@Component
public class PrepareRunApplication implements CommandLineRunner {

    private final TestRunnerService testRunnerService;

    public PrepareRunApplication(TestRunnerService testRunnerService) {
        this.testRunnerService = testRunnerService;
    }

    @Override
    public void run(String... args) throws Exception {
        testRunnerService.run();
    }
}
