package com.mrppa.uniquegen;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class IDGenProviderTest {

    @Test
    void shouldReturnSameObjectForMultipleCalls() {
        IDGenerator idGenerator1 = IDGenProvider.getGenerator(GenerateType.DATE_SEQUENCE_BASED, "Inst1");
        IDGenerator idGenerator2 = IDGenProvider.getGenerator(GenerateType.DATE_SEQUENCE_BASED, "Inst1");
        assertEquals(idGenerator1, idGenerator2);
    }

    @Test
    void shouldThrowErrorForUnknownTypes() {
        assertThrowsExactly(RuntimeException.class, () -> IDGenProvider.getGenerator(null, "Inst1"));
    }

}