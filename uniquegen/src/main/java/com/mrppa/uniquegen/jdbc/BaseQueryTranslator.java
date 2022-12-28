package com.mrppa.uniquegen.jdbc;

import java.sql.Connection;

public class BaseQueryTranslator {

    /**
     * Return queryTranslator implementation by the connection
     *
     * @param connection JDBC connection
     * @return suitable query translator
     */
    public static BaseQueryTranslator findMatchingTranslator(Connection connection) {
        return new BaseQueryTranslator();
    }

    public String generateCreateSequenceScript(String sequenceName) {
        return String.format("CREATE SEQUENCE IF NOT EXISTS %s START WITH 0", sequenceName);
    }

    public String generateSequenceNextValScript(String sequenceName) {
        return String.format("VALUES NEXT VALUE FOR %s", sequenceName);
    }
}
