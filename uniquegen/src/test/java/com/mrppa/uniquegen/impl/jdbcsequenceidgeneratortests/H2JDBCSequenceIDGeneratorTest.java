package com.mrppa.uniquegen.impl.jdbcsequenceidgeneratortests;

import com.mrppa.uniquegen.BaseIDGeneratorTest;
import com.mrppa.uniquegen.ContextBuilder;
import com.mrppa.uniquegen.IDGenerator;
import com.mrppa.uniquegen.IDGeneratorContext;
import com.mrppa.uniquegen.impl.JDBCSequenceIDGenerator;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class H2JDBCSequenceIDGeneratorTest extends BaseIDGeneratorTest {

    DataSource dataSource;

    @BeforeAll
    void init() {
        this.dataSource = getDataSource();
    }

    DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:h2:mem:test");
        dataSource.setUsername("sa");
        dataSource.setPassword("root");
        return dataSource;
    }


    @Test
    void throwErrorWhenMandatoryVariablesMissingFromContext() {
        assertThrowsExactly(RuntimeException.class, () -> new JDBCSequenceIDGenerator(new ContextBuilder().build()));
    }

    @Override
    public IDGenerator createIDGenerator() {
        IDGeneratorContext idGeneratorContext = new ContextBuilder()
                .add(JDBCSequenceIDGenerator.JDBC_DATASOURCE, dataSource)
                .add(JDBCSequenceIDGenerator.SEQUENCE_NAME, "test_Sequence")
                .build();
        return new JDBCSequenceIDGenerator(idGeneratorContext);
    }

}
