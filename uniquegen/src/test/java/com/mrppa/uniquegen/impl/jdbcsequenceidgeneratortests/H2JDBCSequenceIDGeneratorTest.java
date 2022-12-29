package com.mrppa.uniquegen.impl.jdbcsequenceidgeneratortests;

import com.mrppa.uniquegen.BaseIDGeneratorTest;
import com.mrppa.uniquegen.ContextBuilder;
import com.mrppa.uniquegen.IDGenerator;
import com.mrppa.uniquegen.IDGeneratorContext;
import com.mrppa.uniquegen.impl.JDBCSequenceIDGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class H2JDBCSequenceIDGeneratorTest extends BaseIDGeneratorTest {

    Connection connection;

    @BeforeAll
    void init() throws Exception {
        connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
    }

    @Test
    void throwErrorWhenMandatoryVariablesMissingFromContext() {
        assertThrowsExactly(RuntimeException.class, () -> new JDBCSequenceIDGenerator(new ContextBuilder().build()));
    }

    @Override
    public IDGenerator createIDGenerator() {
        IDGeneratorContext idGeneratorContext = new ContextBuilder()
                .add(JDBCSequenceIDGenerator.JDBC_CONNECTION, connection)
                .add(JDBCSequenceIDGenerator.SEQUENCE_NAME, "test_Sequence")
                .build();
        return new JDBCSequenceIDGenerator(idGeneratorContext);
    }

}
