package com.mrppa.uniquegen.jdbc;


public class MariaDBSQLBaseQueryTranslator extends BaseQueryTranslator {
    @Override
    public String generateSequenceNextValScript(String sequenceName) {
        return String.format("SELECT nextval(%s) from dual", sequenceName);
    }
}
