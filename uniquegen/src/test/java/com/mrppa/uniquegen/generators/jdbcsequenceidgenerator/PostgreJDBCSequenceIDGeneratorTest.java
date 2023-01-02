package com.mrppa.uniquegen.generators.jdbcsequenceidgenerator;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class PostgreJDBCSequenceIDGeneratorTest extends H2JDBCSequenceIDGeneratorTest {
    DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:tc:postgresql:9.6.8:///databasename");
        dataSource.setUsername("admin");
        dataSource.setPassword("");
        return dataSource;
    }
}
