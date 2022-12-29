package com.mrppa.uniquegen.impl;

import com.mrppa.uniquegen.BaseIDGeneratorTest;
import com.mrppa.uniquegen.ContextBuilder;
import com.mrppa.uniquegen.IDGenerator;
import com.mrppa.uniquegen.IDGeneratorContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class DateSequenceIDGeneratorTest extends BaseIDGeneratorTest {
    @Override
    public IDGenerator createIDGenerator() {
        IDGeneratorContext idGeneratorContext = new ContextBuilder()
                .add(DateSequenceIDGenerator.CONTEXT_INSTANCE_ID, "inst1")
                .build();
        return new DateSequenceIDGenerator(idGeneratorContext);
    }

    @Test
    void whenInstanceIdOver6DigitsThrowException() {
        assertThrowsExactly(RuntimeException.class, () -> new DateSequenceIDGenerator(
                new ContextBuilder()
                        .add(DateSequenceIDGenerator.CONTEXT_INSTANCE_ID, "extraLongInstanceId")
                        .build()));
    }
}
