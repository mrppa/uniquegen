package com.mrppa.uniquegen;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

abstract class BaseIDGeneratorTest {
    private final Logger logger = Logger.getLogger(this.getClass().getName());


    abstract IDGenerator createIDGenerator();

    @Test
    void successPath() {
        logger.info("Testing success path");
        IDGenerator idGenerator = createIDGenerator();

        String generatedId = idGenerator.generateId();
        logger.info("Generated ID:" + generatedId);

        String generatedId1 = idGenerator.generateId();
        logger.info("Generated ID:" + generatedId1);

        assertNotNull(generatedId1);
        assertNotEquals(generatedId1, generatedId);
    }

    @Test
    void concurrentTest() throws InterruptedException {
        logger.info("Testing concurrency and uniqueness");
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
        logger.info("Concurrent test completed with " + generatedIds.size() + " records");

    }

}