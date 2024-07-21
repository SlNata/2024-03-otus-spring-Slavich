package ru.otus.hw.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.otus.hw.models.mongo.AuthorMongo;
import ru.otus.hw.models.mongo.BookMongo;
import ru.otus.hw.models.mongo.CommentMongo;
import ru.otus.hw.models.mongo.GenreMongo;
import ru.otus.hw.repositories.mongo.MongoAuthorRepository;
import ru.otus.hw.repositories.mongo.MongoBookRepository;
import ru.otus.hw.repositories.mongo.MongoCommentRepository;
import ru.otus.hw.repositories.mongo.MongoGenreRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.hw.config.JobConfig.CURRENT_TIME_MILLIS_NAME;
import static ru.otus.hw.config.JobConfig.IMPORT_FROM_DATABASE_JOB_NAME;

@SpringBootTest
@SpringBatchTest
public class JobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private MongoAuthorRepository authorRepository;

    @Autowired
    private MongoBookRepository bookRepository;

    @Autowired
    private MongoCommentRepository commentRepository;

    @Autowired
    private MongoGenreRepository genreRepository;

    @Autowired
    private MongoOperations mongoOperations;

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    @DisplayName("Тест выгрузки записей в MongoDB")
    void testJob() throws Exception {

        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(IMPORT_FROM_DATABASE_JOB_NAME);

        JobParameters parameters = new JobParametersBuilder()
                .addLong(CURRENT_TIME_MILLIS_NAME, System.currentTimeMillis())
                .toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");

        var authors = mongoOperations.findAll(AuthorMongo.class);
        var genres = mongoOperations.findAll(GenreMongo.class);
        var books = mongoOperations.findAll(BookMongo.class);
        var comments = mongoOperations.findAll(CommentMongo.class);

        assertThat(authorRepository.findAll())
                .isNotEmpty()
                .isEqualTo(authors);

        assertThat(genreRepository.findAll())
                .isNotEmpty()
                .isEqualTo(genres);

        assertThat(bookRepository.findAll())
                .isNotEmpty()
                .isEqualTo(books);

        assertThat(commentRepository.findAll())
                .isNotEmpty()
                .isEqualTo(comments);

    }
}
