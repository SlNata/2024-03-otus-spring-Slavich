package ru.otus.hw.actuators;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
@RequiredArgsConstructor
public class DataBaseConnectActuator implements HealthIndicator {

    private final DataSource dataSource;

    @Override
    public Health health() {
        try {
            Connection connection = dataSource.getConnection();
            return Health.up()
                    .withDetail("message", "DataBase is connect!")
                    .build();

        } catch (Exception exception) {
            return Health.down(exception)
                    .withDetail("message", "DataBase is down!")
                    .build();
        }
    }
}
