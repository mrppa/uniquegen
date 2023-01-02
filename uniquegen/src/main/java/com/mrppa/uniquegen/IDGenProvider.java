package com.mrppa.uniquegen;

import com.mrppa.uniquegen.model.IDGeneratorDefinition;

import java.util.Optional;

public class IDGenProvider {

    private static final IDGenProvidersRegistry idGenProvidersRegistry = new IDGenProvidersRegistry();

    /**
     * Get the IDGenerator by type
     *
     * @param generateType       ID generator type
     * @param idGeneratorContext context with necessary inputs for each implementation
     * @return IDGenerator
     */
    public static synchronized IDGenerator getGenerator(GenerateType generateType,
                                                        IDGeneratorContext idGeneratorContext) {
        return initiateGenerator(generateType, idGeneratorContext);
    }

    /**
     * Get the IDGenerator by type with empty context. May not work with some implementations
     *
     * @param generateType generator type
     * @return IDGenerator
     */
    public static synchronized IDGenerator getGenerator(GenerateType generateType) {
        return getGenerator(generateType, new ContextBuilder().build());

    }

    private static IDGenerator initiateGenerator(GenerateType generateType, IDGeneratorContext idGeneratorContext) {
        Optional<IDGeneratorDefinition> optionalIDGeneratorDefinition = idGenProvidersRegistry
                .getIdGeneratorDefinitionByGenerateType(generateType);
        if (optionalIDGeneratorDefinition.isEmpty()) {
            throw new RuntimeException("Generation type not registered");
        }
        IDGeneratorDefinition idGeneratorDefinition = optionalIDGeneratorDefinition.get();
        idGeneratorDefinition.validateContext(idGeneratorContext);
        return idGeneratorDefinition.instanciateIDGenerator(idGeneratorContext);
    }

}
