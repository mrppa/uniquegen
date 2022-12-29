package com.mrppa.uniquegen;

import com.mrppa.uniquegen.impl.DateSequenceIDGenerator;
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

        assertThrowsExactly(RuntimeException.class, () -> IDGenProvider.getGenerator(null, idGeneratorContext));
    }

}