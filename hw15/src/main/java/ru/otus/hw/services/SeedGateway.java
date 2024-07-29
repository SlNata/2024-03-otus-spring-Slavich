package ru.otus.hw.services;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.hw.models.Seed;
import ru.otus.hw.models.Tree;

import java.util.List;

@MessagingGateway
public interface SeedGateway {

    @Gateway(requestChannel  = "seedChannel", replyChannel = "treeChannel")
    List<Tree> process(List<Seed> seedList);

}
