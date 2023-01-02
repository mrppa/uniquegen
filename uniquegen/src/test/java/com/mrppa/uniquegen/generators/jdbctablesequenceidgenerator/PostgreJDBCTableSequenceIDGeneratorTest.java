package com.mrppa.uniquegen.generators.jdbctablesequenceidgenerator;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class PostgreJDBCTableSequenceIDGeneratorTest extends H2JDBCTableSequenceIDGeneratorTest {
    DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:tc:postgresql:9.6.8:///databasename");
        dataSource.setUsername("admin");
        dataSource.setPassword("");
        dataSource.setMaxTotal(20);
        return dataSource;
    }
}
