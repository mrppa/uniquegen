package com.mrppa.uniquegen;

import com.mrppa.uniquegen.impl.DateSequenceIDGenerator;
import com.mrppa.uniquegen.impl.JDBCSequenceIDGenerator;
import com.mrppa.uniquegen.impl.JDBCTableSequenceIDGenerator;

public class IDGenProvider {

    /**
     * Get the IDGenerator by type
     *
     * @param generateType       ID generator type
     * @param idGeneratorContext context with necessary inputs for each implementation
     * @return IDGenerator
     */
    public static synchronized IDGenerator getGenerator(GenerateType generateType,
                                                        IDGeneratorContext idGeneratorContext) {
        return initiateGenerator(generateType, idGeneratorContext);
    }

    /**
     * Get the IDGenerator by type with empty context. May not work with some implementations
     *
     * @param generateType generator type
     * @return IDGenerator
     */
    public static synchronized IDGenerator getGenerator(GenerateType generateType) {
        return getGenerator(generateType, new ContextBuilder().build());

    }

    private static IDGenerator initiateGenerator(GenerateType generateType, IDGeneratorContext idGeneratorContext) {
        if (GenerateType.DATE_SEQUENCE_BASED.equals(generateType)) {
            return new DateSequenceIDGenerator(idGeneratorContext);
        } else if (GenerateType.JDBC_SEQUENCE_BASED.equals(generateType)) {
            return new JDBCSequenceIDGenerator(idGeneratorContext);
        } else if (GenerateType.JDBC_TABLE_SEQUENCE_BASED.equals(generateType)) {
            return new JDBCTableSequenceIDGenerator(idGeneratorContext);
        }
        throw new RuntimeException("Generation type not recognized");
    }
}
