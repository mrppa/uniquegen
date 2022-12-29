package com.mrppa.uniquegen;

public enum GenerateType {
    /**
     * Implementation in @see com.mrppa.uniquegen.impl.DateSequenceIDGenerator
     */
    DATE_SEQUENCE_BASED,

    /**
     * Implementation in @see com.mrppa.uniquegen.impl.JDBCSequenceIDGenerator
     */
    JDBC_SEQUENCE_BASED,

    /**
     * Implementation in @see com.mrppa.uniquegen.impl.JDBCTableSequenceIDGenerator
     */
    JDBC_TABLE_SEQUENCE_BASED
}
