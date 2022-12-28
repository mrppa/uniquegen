package com.mrppa.uniquegen.impl;

import com.mrppa.uniquegen.IDGenerator;
import com.mrppa.uniquegen.IDGeneratorContext;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Generate unique id 29 digits.<br></br>
 * <code>[Date in format yyyyMMddHHmmssSSS - 17 digits][6 digit sequence][6 digit instance id]</code>
 */
public class DateSequenceIDGenerator extends IDGenerator {
    public static final String CONTEXT_INSTANCE_ID = "INSTANCE_ID";

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    private String instanceId;
    private final AtomicInteger sequence = new AtomicInteger();

    public DateSequenceIDGenerator(IDGeneratorContext idGeneratorContext) {
        super(idGeneratorContext);
        instanceId = idGeneratorContext.getFromContext(CONTEXT_INSTANCE_ID, String.class, "000000");
        if (instanceId.length() > 6) {
            throw new RuntimeException("Instance Id length is more than 6 characters");
        }
        instanceId = StringUtils.leftPad(instanceId, 6, "0");
        sequence.set(0);
    }

    @Override
    public String generateId() {
        String dateComponent = LocalDateTime.now().format(dateFormat);
        String sequenceComponent = StringUtils.leftPad(
                Integer.toString(sequence.updateAndGet(n -> n >= 999999 ? 0 : n + 1)), 6, "0");
        return String.format("%s%s%s", dateComponent, sequenceComponent, instanceId);
    }
}
