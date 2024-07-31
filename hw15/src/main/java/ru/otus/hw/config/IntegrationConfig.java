package ru.otus.hw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.hw.models.Fruit;
import ru.otus.hw.models.FruitType;
import ru.otus.hw.models.Seed;
import ru.otus.hw.models.Tree;
import ru.otus.hw.models.TreeType;
import org.springframework.messaging.Message;

import java.util.Random;
import java.util.random.RandomGenerator;

@Configuration
public class IntegrationConfig {

    private final RandomGenerator randomGenerator = RandomGenerator.getDefault();

    @Bean
    public MessageChannelSpec<?, ?> seedChannel() {
        return MessageChannels.queue(10);
    }

    @Bean
    public MessageChannelSpec<?, ?> treeChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2);
    }

    @Bean
    public IntegrationFlow faunaFlow() {
        return IntegrationFlow.from(seedChannel())
                .split()
                .<Seed, Tree>transform(seed -> new Tree(
                        seed.size(),
                        TreeType.values()[new Random().nextInt(TreeType.values().length)])
                )
                .<Tree, Fruit>transform(tree -> new Fruit(
                        randomGenerator.nextInt(1, 10),
                        randomGenerator.nextInt(10, 100),
                        FruitType.values()[new Random().nextInt(FruitType.values().length)])
                )
                .<Fruit>log(LoggingHandler.Level.INFO, "fruit eat", Message::getPayload)
                .aggregate()
                .channel(treeChannel())
                .get();
    }
}