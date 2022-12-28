package com.mrppa.uniquegen;

import java.util.HashMap;
import java.util.Map;

/**
 * Context based in the IDGenerator implementation
 */
public class ContextBuilder {
    private final Map<String, Object> contextVariables = new HashMap<>();

    /**
     * Add variable to context
     *
     * @param key   variable name
     * @param value variable value
     * @return ContextBuilder
     */
    public ContextBuilder add(String key, Object value) {
        contextVariables.put(key, value);
        return this;
    }


    /**
     * Build the IDGenerator Context
     *
     * @return IDGeneratorContext
     */
    public IDGeneratorContext build() {
        return new IDGeneratorContext(contextVariables);
    }

}
