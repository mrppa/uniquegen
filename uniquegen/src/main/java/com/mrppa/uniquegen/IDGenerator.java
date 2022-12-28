package com.mrppa.uniquegen;

public abstract class IDGenerator {

    IDGeneratorContext idGeneratorContext;

    public IDGenerator(IDGeneratorContext idGeneratorContext){
        this.idGeneratorContext=idGeneratorContext;
    }

    public abstract String generateId();
}
