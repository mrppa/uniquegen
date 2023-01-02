package com.mrppa.uniquegen.impl;

import com.mrppa.uniquegen.BaseIDGeneratorTest;
import com.mrppa.uniquegen.ContextBuilder;
import com.mrppa.uniquegen.IDGenerator;
import com.mrppa.uniquegen.IDGeneratorContext;

public class DateSequenceIDGeneratorTest extends BaseIDGeneratorTest {
    @Override
    public IDGenerator createIDGenerator() {
        IDGeneratorContext idGeneratorContext = new ContextBuilder()
                .add(DateSequenceIDGenerator.CONTEXT_INSTANCE_ID, "inst1")
                .build();
        return new DateSequenceIDGenerator(idGeneratorContext);
    }
}
