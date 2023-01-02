package com.mrppa.uniquegen.generators.jdbctablesequenceidgenerator;

import com.mrppa.uniquegen.BaseIDGeneratorTest;
import com.mrppa.uniquegen.ContextBuilder;
import com.mrppa.uniquegen.IDGenerator;
import com.mrppa.uniquegen.IDGeneratorContext;
import com.mrppa.uniquegen.generators.jdbctablesequenceidgenerator.service.JDBCTableSequenceIDGenerator;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import javax.sql.DataSource;

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
