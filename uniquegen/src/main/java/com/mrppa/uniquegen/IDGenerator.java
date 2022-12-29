package com.mrppa.uniquegen;

public abstract class IDGenerator {

    private final IDGeneratorContext idGeneratorContext;

    public IDGeneratorContext getIdGeneratorContext() {
        return idGeneratorContext;
    }

    public IDGenerator(IDGeneratorContext idGeneratorContext){
        this.idGeneratorContext=idGeneratorContext;
    }

    public abstract String generateId();
}
