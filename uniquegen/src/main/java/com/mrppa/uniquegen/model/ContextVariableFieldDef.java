package com.mrppa.uniquegen.model;

import lombok.Data;

@Data
public class ContextVariableFieldDef {
    private final String variableName;
    private final Class<?> variableType;
    private final boolean mandatory;

    public ContextVariableFieldDef(String variableName, Class<?> variableType, boolean mandatory) {
        this.variableName = variableName;
        this.variableType = variableType;
        this.mandatory = mandatory;
    }

    public ContextVariableFieldDef(String variableName, Class<?> variableType) {
        this(variableName, variableType, false);
    }
}
