package com.mrppa.uniquegen.generators.jdbcsequenceidgenerator.definition;

import com.mrppa.uniquegen.model.GenerateType;
import com.mrppa.uniquegen.IDGenerator;
import com.mrppa.uniquegen.IDGeneratorContext;
import com.mrppa.uniquegen.generators.jdbcsequenceidgenerator.service.JDBCSequenceIDGenerator;
import com.mrppa.uniquegen.model.ContextVariableFieldDef;
import com.mrppa.uniquegen.model.IDGeneratorDefinition;

import javax.sql.DataSource;
import java.util.List;

public class JDBCSequenceIDGeneratorDefinition extends IDGeneratorDefinition {
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
}
