package com.mrppa.uniquegen.generators.jdbctablesequenceidgenerator;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class MySQLJDBCTableSequenceIDGeneratorTest extends H2JDBCTableSequenceIDGeneratorTest {

    DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:tc:mysql:5.7.34:///databasename");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        dataSource.setMaxTotal(20);
        return dataSource;
    }

}
