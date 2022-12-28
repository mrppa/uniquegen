package com.mrppa.uniquegen;

import com.mrppa.uniquegen.impl.DateSequenceIDGenerator;

public class DateSequenceIDGeneratorTest extends BaseIDGeneratorTest {
    @Override
    IDGenerator createIDGenerator() {
        IDGeneratorContext idGeneratorContext = new ContextBuilder()
                .add(DateSequenceIDGenerator.CONTEXT_INSTANCE_ID, "inst1")
                .build();
        return new DateSequenceIDGenerator(idGeneratorContext);
    }

    @Override
    void createIDGeneratorWithExtraLongInstanceId() {
        IDGeneratorContext idGeneratorContext = new ContextBuilder()
                .add(DateSequenceIDGenerator.CONTEXT_INSTANCE_ID, "extraLongInstanceId")
                .build();
        new DateSequenceIDGenerator(idGeneratorContext);
    }
}
