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

    /**
     * Add variable to context
     *
     * @param key   variable name
     * @param value value
     */
    public void addToContext(String key, Object value) {
        contextVariables.put(key, value);
    }

    /**
     * Get variable from the context
     *
     * @param key          variable name
     * @param type         expected class
     * @param defaultValue default value
     * @param <T>
     * @return the variable if exists. Otherwise, return the default value
     */
    public <T> T getFromContext(String key, Class<T> type, T defaultValue) {
        Object value = contextVariables.get(key);
        if (type.isInstance(value)) {
            return type.cast(value);
        }
        return defaultValue;
    }

    /**
     * Validate whether context contains all expected variables
     *
     * @param keys expected context variable names
     * @return true if the context contains all expected. False otherwise
     */
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
