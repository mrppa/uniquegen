package com.mrppa.uniquegen;

import com.mrppa.uniquegen.impl.JDBCSequenceIDGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class JDBCSequenceIDGeneratorTest extends BaseIDGeneratorTest {

    private static Connection connection;

    @BeforeAll
    static void init() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection("jdbc:h2:mem:test;MODE=postgresql", "sa", "");
    }

    @Test
    void throwErrorWhenMandatoryVariablesMissingFromContext() {
        assertThrowsExactly(RuntimeException.class, () -> new JDBCSequenceIDGenerator(new ContextBuilder().build()));
    }

    @Override
    IDGenerator createIDGenerator() {
        IDGeneratorContext idGeneratorContext = new ContextBuilder()
                .add(JDBCSequenceIDGenerator.JDBC_CONNECTION, connection)
                .add(JDBCSequenceIDGenerator.SEQUENCE_NAME, "test_Sequence")
                .build();
        return new JDBCSequenceIDGenerator(idGeneratorContext);
    }

}
