package com.mrppa.uniquegen;

import com.mrppa.uniquegen.impl.DateSequenceIDGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class IDGenProviderTest {

    @Test
    void shouldReturnSameObjectForMultipleCalls() {
        IDGeneratorContext idGeneratorContext = new ContextBuilder()
                .add(DateSequenceIDGenerator.CONTEXT_INSTANCE_ID, "inst1")
                .build();

        IDGenerator idGenerator1 = IDGenProvider.getGenerator(GenerateType.DATE_SEQUENCE_BASED, idGeneratorContext);
        IDGenerator idGenerator2 = IDGenProvider.getGenerator(GenerateType.DATE_SEQUENCE_BASED, idGeneratorContext);
        assertEquals(idGenerator1, idGenerator2);
    }

    @Test
    void shouldThrowErrorForUnknownTypes() {
        IDGeneratorContext idGeneratorContext = new ContextBuilder()
                .add(DateSequenceIDGenerator.CONTEXT_INSTANCE_ID, "inst1")
                .build();

        assertThrowsExactly(RuntimeException.class, () -> IDGenProvider.getGenerator(null, idGeneratorContext));
    }

}