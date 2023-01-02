package com.mrppa.uniquegen.generators.jdbctablesequenceidgenerator.definition;

import com.mrppa.uniquegen.model.GenerateType;
import com.mrppa.uniquegen.IDGenerator;
import com.mrppa.uniquegen.IDGeneratorContext;
import com.mrppa.uniquegen.generators.jdbctablesequenceidgenerator.service.JDBCTableSequenceIDGenerator;
import com.mrppa.uniquegen.model.ContextVariableFieldDef;
import com.mrppa.uniquegen.model.IDGeneratorDefinition;

import javax.sql.DataSource;
import java.util.List;

public class JDBCTableSequenceIDGeneratorDefinition extends IDGeneratorDefinition {
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
}
