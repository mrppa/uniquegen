package com.mrppa.uniquegen.generators.jdbcsequenceidgenerator;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class MariaDBJDBCSequenceIDGeneratorTest extends H2JDBCSequenceIDGeneratorTest {
    DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:tc:mariadb:10.5.5:///databasename");
        dataSource.setUsername("admin");
        dataSource.setPassword("");
        return dataSource;
    }
}
