package com.mrppa.uniquegen;

import com.mrppa.uniquegen.exception.IDGeneratorException;
import com.mrppa.uniquegen.generators.datesequenceidgenerator.service.DateSequenceIDGenerator;
import com.mrppa.uniquegen.model.GenerateType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class IDGenProviderTest {

    @Test
    void testEmptyContext() {
        IDGenerator idGenerator1 = IDGenProvider.getGenerator(GenerateType.DATE_SEQUENCE_BASED);
        assertNotNull(idGenerator1);
    }

    @Test
    void shouldThrowErrorForUnknownTypes() {
        IDGeneratorContext idGeneratorContext = new ContextBuilder()
                .add(DateSequenceIDGenerator.CONTEXT_INSTANCE_ID, "inst1")
                .build();

        assertThrowsExactly(IDGeneratorException.class, () -> IDGenProvider.getGenerator(null, idGeneratorContext));
    }

}