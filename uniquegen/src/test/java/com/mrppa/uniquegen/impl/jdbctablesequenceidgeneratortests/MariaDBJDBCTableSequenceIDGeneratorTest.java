package com.mrppa.uniquegen.impl.jdbctablesequenceidgeneratortests;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class MariaDBJDBCTableSequenceIDGeneratorTest extends H2JDBCTableSequenceIDGeneratorTest {
    DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:tc:mariadb:10.5.5:///databasename");
        dataSource.setUsername("admin");
        dataSource.setPassword("");
        dataSource.setMaxTotal(20);
        return dataSource;
    }
}
