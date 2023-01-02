package com.mrppa.uniquegen.model;

import com.mrppa.uniquegen.IDGenerator;
import com.mrppa.uniquegen.IDGeneratorContext;
import com.mrppa.uniquegen.exception.IDGeneratorException;

import java.util.List;

public abstract class IDGeneratorDefinition {
    public abstract GenerateType getGenerateType();

    public abstract List<ContextVariableFieldDef> getContextVariableDefinitions();

    public abstract IDGenerator instanciateIDGenerator(IDGeneratorContext idGeneratorContext);

    public void validateContext(IDGeneratorContext idGeneratorContext) {
        List<ContextVariableFieldDef> contextVariableFieldDefs = this.getContextVariableDefinitions();
        for (ContextVariableFieldDef contextVariableFieldDef : contextVariableFieldDefs) {
            Object variableValue = idGeneratorContext.getFromContext(contextVariableFieldDef.getVariableName(),
                    contextVariableFieldDef.getVariableType(), null);
            if (contextVariableFieldDef.isMandatory() && variableValue == null) {
                throw new IDGeneratorException("For " + getGenerateType() + " mandatory field " +
                        contextVariableFieldDef.getVariableName() + " is not provided");
            }
        }
    }
}
