package ru.otus.hw.actuators;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.otus.hw.repositories.BookRepository;

@Component
@RequiredArgsConstructor
public class BookHealthIndicator implements HealthIndicator {

    private final BookRepository bookRepository;

    @Override
    public Health health() {
        try {
            return Health.up()
                    .withDetail("message", "Book count %d"
                            .formatted(bookRepository.count())).build();

        } catch (Exception exception) {
            return Health.down()
                    .withDetail("message", exception.getMessage())
                    .build();
        }
    }
}
