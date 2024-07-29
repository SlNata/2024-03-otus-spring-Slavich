package ru.otus.hw.processors;

import jakarta.annotation.Nonnull;
import org.springframework.batch.item.ItemProcessor;
import ru.otus.hw.models.jpa.Comment;
import ru.otus.hw.models.mongo.CommentMongo;

public class CommentProcessor implements ItemProcessor<Comment, CommentMongo> {

    @Override
    public CommentMongo process(@Nonnull Comment item) throws Exception {
        BookProcessor processor = new BookProcessor();
        return new CommentMongo(String.valueOf(item.getId()), item.getTextComment(),
                processor.process(item.getBook()));
    }
}
