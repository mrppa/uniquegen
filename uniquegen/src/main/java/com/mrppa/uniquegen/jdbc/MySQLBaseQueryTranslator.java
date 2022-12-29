package com.mrppa.uniquegen.jdbc;


public class MySQLBaseQueryTranslator extends BaseQueryTranslator{
    @Override
    public String generateCreateSequenceScript(String sequenceName) {
        throw new UnsupportedOperationException("Sequence not supported for MYSQL");
    }

    @Override
    public String generateSequenceNextValScript(String sequenceName) {
        throw new UnsupportedOperationException("Sequence not supported for MYSQL");
    }
}
