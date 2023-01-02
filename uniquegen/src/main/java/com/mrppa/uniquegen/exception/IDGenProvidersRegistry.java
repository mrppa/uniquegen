package com.mrppa.uniquegen.exception;

import com.mrppa.uniquegen.model.GenerateType;
import com.mrppa.uniquegen.IDGenerator;
import com.mrppa.uniquegen.IDGeneratorContext;
import com.mrppa.uniquegen.generators.datesequenceidgenerator.service.DateSequenceIDGenerator;
import com.mrppa.uniquegen.generators.jdbcsequenceidgenerator.service.JDBCSequenceIDGenerator;
import com.mrppa.uniquegen.generators.jdbctablesequenceidgenerator.service.JDBCTableSequenceIDGenerator;
import com.mrppa.uniquegen.model.ContextVariableFieldDef;
import com.mrppa.uniquegen.model.IDGeneratorDefinition;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IDGenProvidersRegistry {
    private final List<IDGeneratorDefinition> idGeneratorDefinitions;

    public IDGenProvidersRegistry() {
        idGeneratorDefinitions = new ArrayList<>();
        registerDateSequenceIDGenerator();
        registerJDBCSequenceIDGenerator();
        registerJDBCTableSequenceIDGenerator();
    }

    public List<IDGeneratorDefinition> getIdGeneratorDefinitions() {
        return idGeneratorDefinitions;
    }

    public Optional<IDGeneratorDefinition> getIdGeneratorDefinitionByGenerateType(GenerateType generateType) {
        return idGeneratorDefinitions.stream().filter(genDef -> generateType.equals(genDef.getGenerateType()))
                .findAny();
    }

    private void registerDateSequenceIDGenerator() {
        idGeneratorDefinitions.add(new IDGeneratorDefinition() {
            @Override
            public GenerateType getGenerateType() {
                return GenerateType.DATE_SEQUENCE_BASED;
            }

            @Override
            public List<ContextVariableFieldDef> getContextVariableDefinitions() {
                return List.of(
                        new ContextVariableFieldDef(DateSequenceIDGenerator.CONTEXT_INSTANCE_ID, String.class)
                );
            }

            @Override
            public IDGenerator instanciateIDGenerator(IDGeneratorContext idGeneratorContext) {
                return new DateSequenceIDGenerator(idGeneratorContext);
            }

            @Override
            public void validateContext(IDGeneratorContext idGeneratorContext) {
                super.validateContext(idGeneratorContext);
                String instanceId = idGeneratorContext.getFromContext(DateSequenceIDGenerator.CONTEXT_INSTANCE_ID,
                        String.class, "000000");
                if (instanceId.length() > 6) {
                    throw new IDGeneratorException("Instance Id length is more than 6 characters");
                }
            }
        });
    }

    private void registerJDBCSequenceIDGenerator() {

        idGeneratorDefinitions.add(new IDGeneratorDefinition() {
            @Override
            public GenerateType getGenerateType() {
                return GenerateType.JDBC_SEQUENCE_BASED;
            }

            @Override
            public List<ContextVariableFieldDef> getContextVariableDefinitions() {
                return List.of(
                        new ContextVariableFieldDef(JDBCSequenceIDGenerator.JDBC_DATASOURCE, DataSource.class, true),
                        new ContextVariableFieldDef(JDBCSequenceIDGenerator.SEQUENCE_NAME, String.class)
                );
            }

            @Override
            public IDGenerator instanciateIDGenerator(IDGeneratorContext idGeneratorContext) {
                return new JDBCSequenceIDGenerator(idGeneratorContext);
            }

        });
    }

    private void registerJDBCTableSequenceIDGenerator() {
        idGeneratorDefinitions.add(new IDGeneratorDefinition() {
            @Override
            public GenerateType getGenerateType() {
                return GenerateType.JDBC_TABLE_SEQUENCE_BASED;
            }

            @Override
            public List<ContextVariableFieldDef> getContextVariableDefinitions() {
                return List.of(
                        new ContextVariableFieldDef(JDBCTableSequenceIDGenerator.JDBC_DATASOURCE, DataSource.class, true),
                        new ContextVariableFieldDef(JDBCTableSequenceIDGenerator.SEQUENCE_NAME, String.class),
                        new ContextVariableFieldDef(JDBCTableSequenceIDGenerator.CACHE_SIZE, Integer.class)
                );
            }

            @Override
            public IDGenerator instanciateIDGenerator(IDGeneratorContext idGeneratorContext) {
                return new JDBCTableSequenceIDGenerator(idGeneratorContext);
            }
        });
    }

}
