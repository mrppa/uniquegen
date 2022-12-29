package com.mrppa.uniquegen.impl.jdbcsequenceidgeneratortests;

import com.mrppa.uniquegen.ContextBuilder;
import com.mrppa.uniquegen.impl.JDBCSequenceIDGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class MySQLJDBCSequenceIDGeneratorTest {

    private static Connection connection;

    @BeforeAll
    static void init() throws SQLException {
        connection = DriverManager.getConnection("jdbc:tc:mysql:5.7.34:///databasename", "root", "");
    }

    @Test
    void throwUnsupportedExceptionWhenTryingToInitiate() {
        assertThrowsExactly(UnsupportedOperationException.class, () -> new JDBCSequenceIDGenerator(new ContextBuilder()
                .add(JDBCSequenceIDGenerator.JDBC_CONNECTION, connection)
                .build()));
    }
}
