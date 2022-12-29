package com.mrppa.uniquegen.jdbc;


public class H2SQLBaseQueryTranslator extends BaseQueryTranslator {
    @Override
    public String generateSequenceNextValScript(String sequenceName) {
        return String.format("VALUES NEXT VALUE FOR %s", sequenceName);
    }
}
