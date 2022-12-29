package com.mrppa.uniquegen.impl.jdbcsequenceidgeneratortests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.sql.DriverManager;
import java.sql.SQLException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostgreJDBCSequenceIDGeneratorTest extends H2JDBCSequenceIDGeneratorTest {

    @BeforeAll
    void init() throws SQLException {
        connection = DriverManager.getConnection("jdbc:tc:postgresql:9.6.8:///databasename", "admin", "");
    }
}
