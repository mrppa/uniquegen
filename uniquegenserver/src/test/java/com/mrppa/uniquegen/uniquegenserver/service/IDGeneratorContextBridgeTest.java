package com.mrppa.uniquegen.uniquegenserver.service;

import com.mrppa.uniquegen.model.GenerateType;
import com.mrppa.uniquegen.exception.IDGenProvidersRegistry;
import com.mrppa.uniquegen.IDGenerator;
import com.mrppa.uniquegen.IDGeneratorContext;
import com.mrppa.uniquegen.model.ContextVariableFieldDef;
import com.mrppa.uniquegen.model.IDGeneratorDefinition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IDGeneratorContextBridgeTest {

    @Mock
    IDGenProvidersRegistry idGenProvidersRegistry;

    @Mock
    Environment env;

    IDGeneratorContextBridge idGeneratorContextBridge;

    @BeforeEach
    void init() {
        idGeneratorContextBridge = new IDGeneratorContextBridge();
        idGeneratorContextBridge.idGenProvidersRegistry = idGenProvidersRegistry;
        idGeneratorContextBridge.env = env;

        IDGeneratorDefinition definition = new IDGeneratorDefinition() {
            @Override
            public GenerateType getGenerateType() {
                return GenerateType.DATE_SEQUENCE_BASED;
            }

            @Override
            public List<ContextVariableFieldDef> getContextVariableDefinitions() {
                return List.of(
                        new ContextVariableFieldDef("v1", String.class),
                        new ContextVariableFieldDef("v2", String.class),
                        new ContextVariableFieldDef("v3", Integer.class),
                        new ContextVariableFieldDef("v4", DataSource.class)
                );
            }

            @Override
            public IDGenerator instanciateIDGenerator(IDGeneratorContext idGeneratorContext) {
                return null;
            }
        };
        when(idGenProvidersRegistry.getIdGeneratorDefinitionByGenerateType(any())).thenReturn(Optional.of(definition));

    }

    @Test
    void testGeneratorContext() {
        when(env.containsProperty("uniquegenserver.idgenerator.date_sequence_based.v1")).thenReturn(true);
        when(env.getProperty("uniquegenserver.idgenerator.date_sequence_based.v1", String.class)).thenReturn("hello");

        when(env.containsProperty("uniquegenserver.idgenerator.date_sequence_based.v2")).thenReturn(true);
        when(env.getProperty("uniquegenserver.idgenerator.date_sequence_based.v2", String.class)).thenReturn("hello2");

        when(env.containsProperty("uniquegenserver.idgenerator.date_sequence_based.v3")).thenReturn(true);
        when(env.getProperty("uniquegenserver.idgenerator.date_sequence_based.v3", Integer.class)).thenReturn(11);

        when(env.containsProperty("uniquegenserver.idgenerator.date_sequence_based.v4.url")).thenReturn(true);
        when(env.getProperty("uniquegenserver.idgenerator.date_sequence_based.v4.url")).thenReturn("jdbc:h2:mem:testDb");

        IDGeneratorContext idGeneratorContext = idGeneratorContextBridge.generatorContext(GenerateType.DATE_SEQUENCE_BASED);
        assertNotNull(idGeneratorContext);
        assertEquals("hello", idGeneratorContext.getFromContext("v1", String.class, null));
        assertEquals("hello2", idGeneratorContext.getFromContext("v2", String.class, null));
        assertEquals(11, idGeneratorContext.getFromContext("v3", Integer.class, null));
        assertNotNull(idGeneratorContext.getFromContext("v4", DataSource.class, null));
    }

    @Test
    void whenMissingPropertiesShouldIgnore() {
        when(env.containsProperty("uniquegenserver.idgenerator.date_sequence_based.v1")).thenReturn(true);
        when(env.getProperty("uniquegenserver.idgenerator.date_sequence_based.v1", String.class)).thenReturn("hello");

        when(env.containsProperty("uniquegenserver.idgenerator.date_sequence_based.v2")).thenReturn(false);

        when(env.containsProperty("uniquegenserver.idgenerator.date_sequence_based.v3")).thenReturn(true);
        when(env.getProperty("uniquegenserver.idgenerator.date_sequence_based.v3", Integer.class)).thenReturn(11);

        when(env.containsProperty("uniquegenserver.idgenerator.date_sequence_based.v4.url")).thenReturn(false);

        IDGeneratorContext idGeneratorContext = idGeneratorContextBridge.generatorContext(GenerateType.DATE_SEQUENCE_BASED);
        assertNotNull(idGeneratorContext);
        assertEquals("hello", idGeneratorContext.getFromContext("v1", String.class, null));
        assertNull(idGeneratorContext.getFromContext("v2", String.class, null));
        assertEquals(11, idGeneratorContext.getFromContext("v3", Integer.class, null));
        assertNull(idGeneratorContext.getFromContext("v4", DataSource.class, null));
    }
}