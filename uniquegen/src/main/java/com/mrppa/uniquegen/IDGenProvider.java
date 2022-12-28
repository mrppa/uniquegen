package com.mrppa.uniquegen;

import com.mrppa.uniquegen.impl.DateSequenceIDGenerator;

import java.util.HashMap;
import java.util.Map;

public class IDGenProvider {

    private static final Map<GenerateType, IDGenerator> generatorMap = new HashMap<>();

    public static synchronized IDGenerator getGenerator(GenerateType generateType,
                                                        IDGeneratorContext idGeneratorContext) {
        IDGenerator idGenerator = generatorMap.get(generateType);
        if (idGenerator == null) {
            idGenerator = initiateGenerator(generateType, idGeneratorContext);
            generatorMap.put(generateType, idGenerator);
        }
        return idGenerator;
    }

    private static IDGenerator initiateGenerator(GenerateType generateType, IDGeneratorContext idGeneratorContext) {
        if (GenerateType.DATE_SEQUENCE_BASED.equals(generateType)) {
            return new DateSequenceIDGenerator(idGeneratorContext);
        }
        throw new RuntimeException("Generation type not recognized");
    }
}
