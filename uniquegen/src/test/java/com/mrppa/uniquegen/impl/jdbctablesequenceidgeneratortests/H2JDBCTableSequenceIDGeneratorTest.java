package com.mrppa.uniquegen.impl.jdbctablesequenceidgeneratortests;

import com.mrppa.uniquegen.BaseIDGeneratorTest;
import com.mrppa.uniquegen.ContextBuilder;
import com.mrppa.uniquegen.IDGenerator;
import com.mrppa.uniquegen.IDGeneratorContext;
import com.mrppa.uniquegen.impl.JDBCSequenceIDGenerator;
import com.mrppa.uniquegen.impl.JDBCTableSequenceIDGenerator;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class H2JDBCTableSequenceIDGeneratorTest extends BaseIDGeneratorTest {

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
        dataSource.setMaxTotal(20);
        return dataSource;
    }


    @Test
    void throwErrorWhenMandatoryVariablesMissingFromContext() {
        assertThrowsExactly(RuntimeException.class, () -> new JDBCSequenceIDGenerator(new ContextBuilder().build()));
    }

    @Override
    public IDGenerator createIDGenerator() {
        IDGeneratorContext idGeneratorContext = new ContextBuilder()
                .add(JDBCTableSequenceIDGenerator.JDBC_DATASOURCE, dataSource)
                .add(JDBCTableSequenceIDGenerator.SEQUENCE_NAME, "test_Sequence")
                .add(JDBCTableSequenceIDGenerator.CACHE_SIZE, 1000)
                .build();
        return new JDBCTableSequenceIDGenerator(idGeneratorContext);
    }

}
