package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ServiceImplTest {
    @Mock
    TestService testService;
    private TestRunnerService testRunnerService;

    @BeforeEach
    void setUp() {
        testRunnerService = new TestRunnerServiceImpl(testService);
    }
    @Test
    void testRun() {
        testRunnerService.run();
        verify(testService, times(1)).executeTest();
    }
}
