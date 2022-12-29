package com.mrppa.uniquegen.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class BaseQueryTranslator {
    private static final Logger logger = Logger.getLogger(BaseQueryTranslator.class.getName());

    /**
     * Return queryTranslator implementation by the connection
     *
     * @param connection JDBC connection
     * @return suitable query translator
     */
    public static BaseQueryTranslator findMatchingTranslator(Connection connection) throws SQLException {
        BaseQueryTranslator translator = null;
        String dbSystem = connection.getMetaData().getDatabaseProductName();
        if (dbSystem.toUpperCase().contains("MYSQL")) {
            translator = new MySQLBaseQueryTranslator();
        } else if (dbSystem.toUpperCase().contains("H2")) {
            translator = new H2SQLBaseQueryTranslator();
        } else if (dbSystem.toUpperCase().contains("MARIADB")) {
            translator = new MariaDBSQLBaseQueryTranslator();
        }
        if (translator == null) {
            translator = new BaseQueryTranslator();
        }
        logger.info("Db system :" + dbSystem + " implementation:" + translator);
        return translator;
    }

    public String generateCreateSequenceScript(String sequenceName) {
        return String.format("CREATE SEQUENCE IF NOT EXISTS %s START WITH 1", sequenceName);
    }

    public String generateSequenceNextValScript(String sequenceName) {
        return String.format("SELECT nextval('%s')", sequenceName);
    }

    public String generateCreateSequenceTableScript() {
        return String.format("""
                        CREATE TABLE IF NOT EXISTS %s (
                            sequence_name varchar(30) NOT NULL,
                            next_val varchar(20) NOT NULL,
                            PRIMARY KEY (sequence_name)
                        );"""
                , "uniquegen_sequence");
    }

    public String generateInsertRecordToSequenceTableScript(String sequenceName) {
        return String.format("""
                        INSERT INTO %s (sequence_name, next_val) VALUES ('%s','%s');"""
                , "uniquegen_sequence", sequenceName, "1");
    }

    public String generateFetchTableRecordSequenceValue(boolean lockRecord) {
        String lockStatement = lockRecord ? " FOR UPDATE " : "";
        return String.format("""
                        SELECT sequence_name,next_val from %s WHERE sequence_name = ? %s
                        """
                , "uniquegen_sequence", lockStatement);
    }

    public String generateUpdateTableRecordSequenceValue() {
        return String.format("""
                        UPDATE %s SET next_val=? WHERE sequence_name = ?
                        """
                , "uniquegen_sequence");
    }
}
