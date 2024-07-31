package ru.otus.hw.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.models.Seed;
import ru.otus.hw.models.SeedTree;
import ru.otus.hw.models.Tree;
import ru.otus.hw.services.SeedGateway;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SeedGatewayTest {

    @Autowired
    SeedGateway seedGateway;

    @Test
    void test() {

        List<Tree> resultTreeOne = seedGateway.process(Collections.singletonList(new Seed(2, SeedTree.OAK)));
        List<Tree> resultTreeTwo = seedGateway.process(Collections.singletonList(new Seed(1, SeedTree.PEAR)));

        assertNotNull(resultTreeOne);
        assertNotNull(resultTreeTwo);
    }
}
