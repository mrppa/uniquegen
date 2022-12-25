package com.mrppa.uniquegen;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

abstract class BaseIDGeneratorTest {

    abstract IDGenerator createIDGenerator();

    abstract void createIDGeneratorWithExtraLongInstanceId();

    @Test
    void successPath() {
        IDGenerator idGenerator = createIDGenerator();
        String generatedId = idGenerator.generateId();
        assertNotNull(generatedId);
        assertEquals(29, generatedId.length());
    }

    @Test
    void whenInstanceIdOver6DigitsThrowException() {
        assertThrowsExactly(RuntimeException.class, this::createIDGeneratorWithExtraLongInstanceId);
    }

    @Test
    void concurrentTest() throws InterruptedException {
        int nuOfGenerations = 1000000;
        List<String> generatedIds = Collections.synchronizedList(new ArrayList<>());
        IDGenerator idGenerator = createIDGenerator();
        Runnable generateRun = () -> {
            String id = idGenerator.generateId();
            generatedIds.add(id);
        };
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < nuOfGenerations; i++) {
            executor.execute(generateRun);
        }
        executor.shutdown();
        executor.awaitTermination(60, TimeUnit.SECONDS);
        assertEquals(nuOfGenerations, generatedIds.size());
        Set<String> generatedIdSet = new HashSet<>(generatedIds);
        assertEquals(generatedIds.size(), generatedIdSet.size());
    }

}