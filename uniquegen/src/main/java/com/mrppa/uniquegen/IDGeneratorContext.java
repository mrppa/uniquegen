package com.mrppa.uniquegen;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class IDGeneratorContext {
    private static final Logger logger = Logger.getLogger(IDGeneratorContext.class.getName());
    private final Map<String, Object> contextVariables;

    public IDGeneratorContext() {
        contextVariables = new HashMap<>();
    }

    public IDGeneratorContext(Map<String, Object> contextVariables) {
        this();
        this.contextVariables.putAll(contextVariables);
    }


    public void addToContext(String key, Object value) {
        contextVariables.put(key, value);
    }

    public <T> T getFromContext(String key, Class<T> type, T defaultValue) {
        Object value = contextVariables.get(key);
        if (type.isInstance(value)) {
            return (T) value;
        }
        return defaultValue;
    }

    public boolean checkVariableAvailability(String... keys) {
        boolean hasAllKeys = true;
        for (String key : keys) {
            if (!contextVariables.containsKey(key)) {
                hasAllKeys = false;
                logger.warning("Required context variable " + key + " missing");
            }
        }
        return hasAllKeys;
    }
}
