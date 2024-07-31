package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.Seed;
import ru.otus.hw.models.SeedTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.random.RandomGenerator;

@Service
@RequiredArgsConstructor
public class SeedService {

    private final SeedGateway seedGateway;

    private final RandomGenerator randomGenerator = RandomGenerator.getDefault();

    public void startGenerateSeedLoop() {
        ForkJoinPool.commonPool().execute(() -> {
            List<Seed> seedList = generateSeeds();
            seedGateway.process(seedList);
        });
    }

    private List<Seed> generateSeeds() {
        int seedCount = randomGenerator.nextInt(10, 100);
        List<Seed> seedList = new ArrayList<>(seedCount);
        for (int i = 0; i < seedCount; i++) {
            seedList.add(generateSeed());
        }
        return seedList;
    }

    private Seed generateSeed() {
        int size = randomGenerator.nextInt(5, 10);
        SeedTree type = SeedTree.values()[new Random().nextInt(SeedTree.values().length)];
        return new Seed(size, type);
    }
}
