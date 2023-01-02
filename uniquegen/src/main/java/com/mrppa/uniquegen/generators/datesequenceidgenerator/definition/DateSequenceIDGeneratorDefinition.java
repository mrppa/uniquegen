package com.mrppa.uniquegen.generators.datesequenceidgenerator.definition;

import com.mrppa.uniquegen.model.GenerateType;
import com.mrppa.uniquegen.IDGenerator;
import com.mrppa.uniquegen.IDGeneratorContext;
import com.mrppa.uniquegen.exception.IDGeneratorException;
import com.mrppa.uniquegen.generators.datesequenceidgenerator.service.DateSequenceIDGenerator;
import com.mrppa.uniquegen.model.ContextVariableFieldDef;
import com.mrppa.uniquegen.model.IDGeneratorDefinition;

import java.util.List;

public class DateSequenceIDGeneratorDefinition extends IDGeneratorDefinition {
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
}
