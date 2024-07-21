package ru.otus.hw.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.hw.models.jpa.Author;
import ru.otus.hw.models.jpa.Book;
import ru.otus.hw.models.jpa.Comment;
import ru.otus.hw.models.jpa.Genre;
import ru.otus.hw.models.mongo.AuthorMongo;
import ru.otus.hw.models.mongo.BookMongo;
import ru.otus.hw.models.mongo.CommentMongo;
import ru.otus.hw.models.mongo.GenreMongo;
import ru.otus.hw.processors.AuthorProcessor;
import ru.otus.hw.processors.BookProcessor;
import ru.otus.hw.processors.CommentProcessor;
import ru.otus.hw.processors.GenreProcessor;
import ru.otus.hw.repositories.jpa.AuthorRepository;
import ru.otus.hw.repositories.jpa.BookRepository;
import ru.otus.hw.repositories.jpa.CommentRepository;
import ru.otus.hw.repositories.jpa.GenreRepository;

import java.util.Collections;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class JobConfig {

    public static final String CURRENT_TIME_MILLIS_NAME = "currentTimeMillis";

        public static final String IMPORT_FROM_DATABASE_JOB_NAME = "importFromDatabaseJob";

        private static final int CHUNKSIZE = 10;

        private final MongoTemplate mongoTemplate;

        private final JobRepository jobRepository;

        private final PlatformTransactionManager platformTransactionManager;

        private final AuthorRepository authorRepository;

        private final GenreRepository genreRepository;

        private final BookRepository bookRepository;

        private final CommentRepository commentRepository;

        private final Map<String, Sort.Direction> sortsMap =
                Collections.singletonMap("id", Sort.Direction.ASC);

        @StepScope
        @Bean
        public RepositoryItemReader<Author> authorItemReader() {
            RepositoryItemReader<Author> reader = new RepositoryItemReader<>();
            reader.setName("authorReader");

            reader.setSort(sortsMap);
            reader.setRepository(authorRepository);
            reader.setMethodName("findAll");
            reader.setPageSize(CHUNKSIZE);
            return reader;
        }

        @StepScope
        @Bean
        public RepositoryItemReader<Genre> genreItemReader() {
            RepositoryItemReader<Genre> reader = new RepositoryItemReader<>();
            reader.setName("genreReader");

            reader.setSort(sortsMap);
            reader.setRepository(genreRepository);
            reader.setMethodName("findAll");
            reader.setPageSize(CHUNKSIZE);
            return reader;
        }

        @StepScope
        @Bean
        public RepositoryItemReader<Book> bookItemReader() {
            RepositoryItemReader<Book> reader = new RepositoryItemReader<>();
            reader.setName("bookReader");

            reader.setSort(sortsMap);
            reader.setRepository(bookRepository);
            reader.setMethodName("findAll");
            reader.setPageSize(CHUNKSIZE);
            return reader;
        }

        @StepScope
        @Bean
        public RepositoryItemReader<Comment> commentItemReader() {
            RepositoryItemReader<Comment> reader = new RepositoryItemReader<>();
            reader.setName("commentReader");

            reader.setSort(sortsMap);
            reader.setRepository(commentRepository);
            reader.setMethodName("findAll");
            reader.setPageSize(CHUNKSIZE);
            return reader;
        }


        @Bean
        public AuthorProcessor authorProcessor() {
            return new AuthorProcessor();
        }

        @Bean
        public GenreProcessor genreProcessorProcessor() {
            return new GenreProcessor();
        }

        @Bean
        public BookProcessor bookProcessorProcessor() {
            return new BookProcessor();
        }

        @Bean
        public CommentProcessor commentProcessorProcessor() {
            return new CommentProcessor();
        }

        @StepScope
        @Bean
        public MongoItemWriter<AuthorMongo> authorWriter() {
            MongoItemWriter<AuthorMongo> writer = new MongoItemWriter<>();
            writer.setTemplate(mongoTemplate);
            writer.setCollection("authors");
            return writer;
        }

        @StepScope
        @Bean
        public MongoItemWriter<GenreMongo> genreWriter() {
            MongoItemWriter<GenreMongo> writer = new MongoItemWriter<>();
            writer.setTemplate(mongoTemplate);
            writer.setCollection("genres");
            return writer;
        }

        @StepScope
        @Bean
        public MongoItemWriter<BookMongo> bookWriter() {
            MongoItemWriter<BookMongo> writer = new MongoItemWriter<>();
            writer.setTemplate(mongoTemplate);
            writer.setCollection("books");
            return writer;
        }

        @StepScope
        @Bean
        public MongoItemWriter<CommentMongo> commentWriter() {
            MongoItemWriter<CommentMongo> writer = new MongoItemWriter<>();
            writer.setTemplate(mongoTemplate);
            writer.setCollection("comments");
            return writer;
        }

        @Bean
        public Job importFromDatabaseJob(Step transformAuthorStep,
                                         Step transformGenreStep,
                                         Step transformBookStep,
                                         Step transformCommentStep) {
            return new JobBuilder(IMPORT_FROM_DATABASE_JOB_NAME, jobRepository)
                    .incrementer(new RunIdIncrementer())
                    .flow(transformAuthorStep)
                    .next(transformGenreStep)
                    .next(transformBookStep)
                    .next(transformCommentStep)
                    .end()
                    .build();
        }

        @Bean
        public Step transformAuthorStep(RepositoryItemReader<Author> reader,
                                        MongoItemWriter<AuthorMongo> writer,
                                        AuthorProcessor processor) {
            return new StepBuilder("transformAuthorStep", jobRepository)
                    .<Author, AuthorMongo>chunk(CHUNKSIZE, platformTransactionManager)
                    .reader(reader)
                    .processor(processor)
                    .writer(writer)
                    .build();
        }

        @Bean
        public Step transformGenreStep(RepositoryItemReader<Genre> reader,
                                       MongoItemWriter<GenreMongo> writer,
                                       GenreProcessor processor) {
            return new StepBuilder("transformGenreStep", jobRepository)
                    .<Genre, GenreMongo>chunk(CHUNKSIZE, platformTransactionManager)
                    .reader(reader)
                    .processor(processor)
                    .writer(writer)
                    .build();
        }

    @Bean
    public Step transformBookStep(RepositoryItemReader<Book> reader,
                                  MongoItemWriter<BookMongo> writer,
                                  BookProcessor processor) {
        return new StepBuilder("transformBookStep", jobRepository)
                .<Book, BookMongo>chunk(CHUNKSIZE, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step transformCommentStep(RepositoryItemReader<Comment> reader,
                                     MongoItemWriter<CommentMongo> writer,
                                     CommentProcessor processor) {
        return new StepBuilder("transformCommentStep", jobRepository)
                .<Comment, CommentMongo>chunk(CHUNKSIZE, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}