package com.mrppa.uniquegen.generators;

import com.mrppa.uniquegen.generators.datesequenceidgenerator.definition.DateSequenceIDGeneratorDefinition;
import com.mrppa.uniquegen.generators.jdbcsequenceidgenerator.definition.JDBCSequenceIDGeneratorDefinition;
import com.mrppa.uniquegen.generators.jdbctablesequenceidgenerator.definition.JDBCTableSequenceIDGeneratorDefinition;
import com.mrppa.uniquegen.model.GenerateType;
import com.mrppa.uniquegen.model.IDGeneratorDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GeneratorRegistry {
    private final List<IDGeneratorDefinition> idGeneratorDefinitions;

    public GeneratorRegistry() {
        idGeneratorDefinitions = new ArrayList<>();
        registerGeneratorDefinitions();
    }

    private void registerGeneratorDefinitions() {
        idGeneratorDefinitions.add(new DateSequenceIDGeneratorDefinition());
        idGeneratorDefinitions.add(new JDBCSequenceIDGeneratorDefinition());
        idGeneratorDefinitions.add(new JDBCTableSequenceIDGeneratorDefinition());
    }


    public Optional<IDGeneratorDefinition> getDefinitionByType(GenerateType generateType) {
        return idGeneratorDefinitions.stream().filter(genDef -> genDef.getGenerateType().equals(generateType))
                .findAny();
    }

}
