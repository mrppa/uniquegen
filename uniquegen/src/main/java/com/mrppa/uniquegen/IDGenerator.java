package com.mrppa.uniquegen;

import java.util.List;

public abstract class IDGenerator {

    private final IDGeneratorContext idGeneratorContext;

    public IDGeneratorContext getIdGeneratorContext() {
        return idGeneratorContext;
    }

    public IDGenerator(IDGeneratorContext idGeneratorContext) {
        this.idGeneratorContext = idGeneratorContext;
    }

    /**
     * Generate Id
     *
     * @return generated id
     */
    public String generateId() {
        List<String> generatedIds = generateIds(1);
        if (!generatedIds.isEmpty()) {
            return generatedIds.get(0);
        }
        return null;
    }

    /**
     * Generate list of ids
     *
     * @param numberOfIds number of ids required
     * @return list of id
     */
    public abstract List<String> generateIds(int numberOfIds);
}
