package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import static ru.otus.hw.config.JobConfig.CURRENT_TIME_MILLIS_NAME;

@RequiredArgsConstructor
@ShellComponent
@Slf4j
public class BatchCommands {

    private final Job importFromDatabaseJob;

    private final JobLauncher jobLauncher;

    @SuppressWarnings("unused")
    @ShellMethod(value = "startMigrationJobWithJobLauncher", key = "sm-jl")
    public void startMigrationJobWithJobLauncher() throws Exception {
        JobExecution execution = jobLauncher.run(importFromDatabaseJob, new JobParametersBuilder()
                .addLong(CURRENT_TIME_MILLIS_NAME, System.currentTimeMillis())
                .toJobParameters());
        System.out.println(execution);
    }

}
