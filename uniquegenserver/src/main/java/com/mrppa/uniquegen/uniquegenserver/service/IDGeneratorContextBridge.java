package com.mrppa.uniquegen.uniquegenserver.service;

import com.mrppa.uniquegen.model.GenerateType;
import com.mrppa.uniquegen.exception.IDGenProvidersRegistry;
import com.mrppa.uniquegen.IDGeneratorContext;
import com.mrppa.uniquegen.exception.IDGeneratorException;
import com.mrppa.uniquegen.model.ContextVariableFieldDef;
import com.mrppa.uniquegen.model.IDGeneratorDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Optional;

@Component
public class IDGeneratorContextBridge {
    private static final Logger logger = LoggerFactory.getLogger(IDGeneratorContextBridge.class);
    @Autowired
    IDGenProvidersRegistry idGenProvidersRegistry;
    @Autowired
    Environment env;

    public IDGeneratorContext generatorContext(GenerateType generateType) {
        logger.debug("Generating context for {}", generateType);
        Optional<IDGeneratorDefinition> optionalIDGeneratorDefinition = idGenProvidersRegistry
                .getIdGeneratorDefinitionByGenerateType(generateType);
        if (optionalIDGeneratorDefinition.isEmpty()) {
            throw new IDGeneratorException("Generation type not registered");
        }
        IDGeneratorDefinition idGeneratorDefinition = optionalIDGeneratorDefinition.get();

        IDGeneratorContext idGeneratorContext = new IDGeneratorContext();
        for (ContextVariableFieldDef contextVariableFieldDef : idGeneratorDefinition.getContextVariableDefinitions()) {
            Object variableValue = retriveContextVariableValue(idGeneratorDefinition, contextVariableFieldDef);
            logger.debug("Context variable {} with value {} ", contextVariableFieldDef, variableValue);
            idGeneratorContext.addToContext(contextVariableFieldDef.getVariableName(), variableValue);
        }
        idGeneratorDefinition.validateContext(idGeneratorContext);
        return idGeneratorContext;
    }

    private Object retriveContextVariableValue(IDGeneratorDefinition idGeneratorDefinition, ContextVariableFieldDef contextVariableFieldDef) {
        Object variableValue = null;
        String serverVariableName = String.format("uniquegenserver.idgenerator.%s.%s",
                idGeneratorDefinition.getGenerateType(), contextVariableFieldDef.getVariableName()).toLowerCase();

        if (DataSource.class.equals(contextVariableFieldDef.getVariableType())) {
            try {
                variableValue = generateDataSource(idGeneratorDefinition, contextVariableFieldDef);
            } catch (Exception e) {
                logger.warn("Datasource generation failed for {}", contextVariableFieldDef, e);
            }
        } else {
            //checking from environment variable
            if (env.containsProperty(serverVariableName)) {
                variableValue = env.getProperty(serverVariableName, contextVariableFieldDef.getVariableType());
            }
        }
        return variableValue;
    }

    private DataSource generateDataSource(IDGeneratorDefinition idGeneratorDefinition, ContextVariableFieldDef contextVariableFieldDef) {
        String serverVariableName = String.format("uniquegenserver.idgenerator.%s.%s",
                idGeneratorDefinition.getGenerateType(), contextVariableFieldDef.getVariableName()).toLowerCase();
        String urlPropName = String.format("%s.url", serverVariableName);
        if (!env.containsProperty(urlPropName)) {
            logger.warn("Missing expected property {}. Datasource will not be created", urlPropName);
            return null;
        }
        String url = env.getProperty(urlPropName);
        String username = env.getProperty(String.format("%s.username", serverVariableName));
        String password = env.getProperty(String.format("%s.password", serverVariableName));
        return DataSourceBuilder.create().url(url).username(username).password(password).build();
    }
}
