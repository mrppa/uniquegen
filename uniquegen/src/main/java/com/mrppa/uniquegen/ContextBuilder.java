package com.mrppa.uniquegen;

import java.util.HashMap;
import java.util.Map;

public class ContextBuilder {
    private final Map<String, Object> contextVariables = new HashMap<>();

    public ContextBuilder add(String key, Object value) {
        contextVariables.put(key, value);
        return this;
    }

    public IDGeneratorContext build() {
        return new IDGeneratorContext(contextVariables);
    }

}
