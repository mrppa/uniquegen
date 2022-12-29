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
}
