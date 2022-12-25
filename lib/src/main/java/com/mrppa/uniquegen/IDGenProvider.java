package com.mrppa.uniquegen;

import com.mrppa.uniquegen.impl.DateSequenceIDGenerator;

import java.util.HashMap;
import java.util.Map;

public class IDGenProvider {

    private static final Map<GenerateType, IDGenerator> generatorMap = new HashMap<>();

    public static synchronized IDGenerator getGenerator(GenerateType generateType, String instanceId) {
        IDGenerator idGenerator = generatorMap.get(generateType);
        if (idGenerator == null) {
            idGenerator = initiateGenerator(generateType, instanceId);
            generatorMap.put(generateType, idGenerator);
        }
        return idGenerator;
    }

    private static IDGenerator initiateGenerator(GenerateType generateType, String instanceId) {
        if (GenerateType.DATE_SEQUENCE_BASED.equals(generateType)) {
            return new DateSequenceIDGenerator(instanceId);
        }
        throw new RuntimeException("Generation type not recognized");
    }
}
