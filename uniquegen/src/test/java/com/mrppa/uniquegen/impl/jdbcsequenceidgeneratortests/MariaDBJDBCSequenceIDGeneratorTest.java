package com.mrppa.uniquegen.impl.jdbcsequenceidgeneratortests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.sql.DriverManager;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MariaDBJDBCSequenceIDGeneratorTest extends H2JDBCSequenceIDGeneratorTest {

    @BeforeAll
    void init() throws Exception {
        connection = DriverManager.getConnection("jdbc:tc:mariadb:10.5.5:///databasename", "admin", "");
    }
}
