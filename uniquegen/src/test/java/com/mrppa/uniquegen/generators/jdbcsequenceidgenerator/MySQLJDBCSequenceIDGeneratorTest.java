package com.mrppa.uniquegen.generators.jdbcsequenceidgenerator;

import com.mrppa.uniquegen.ContextBuilder;
import com.mrppa.uniquegen.generators.jdbcsequenceidgenerator.service.JDBCSequenceIDGenerator;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class MySQLJDBCSequenceIDGeneratorTest {

    DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:tc:mysql:5.7.34:///databasename");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        return dataSource;
    }

    @Test
    void throwUnsupportedExceptionWhenTryingToInitiate() {
        assertThrowsExactly(UnsupportedOperationException.class, () -> new JDBCSequenceIDGenerator(new ContextBuilder()
                .add(JDBCSequenceIDGenerator.JDBC_DATASOURCE, getDataSource())
                .build()));
    }
}
