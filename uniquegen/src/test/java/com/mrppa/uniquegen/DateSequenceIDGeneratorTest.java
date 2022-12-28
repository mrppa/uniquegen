package com.mrppa.uniquegen;

import com.mrppa.uniquegen.impl.DateSequenceIDGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class DateSequenceIDGeneratorTest extends BaseIDGeneratorTest {
    @Override
    IDGenerator createIDGenerator() {
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
