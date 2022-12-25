package com.mrppa.uniquegen;

import com.mrppa.uniquegen.impl.DateSequenceIDGenerator;

public class DateSequenceIDGeneratorTest extends BaseIDGeneratorTest {
    @Override
    IDGenerator createIDGenerator() {
        return new DateSequenceIDGenerator("inst1");
    }

    @Override
    void createIDGeneratorWithExtraLongInstanceId() {
        new DateSequenceIDGenerator("extraLongInstanceId");
    }
}
