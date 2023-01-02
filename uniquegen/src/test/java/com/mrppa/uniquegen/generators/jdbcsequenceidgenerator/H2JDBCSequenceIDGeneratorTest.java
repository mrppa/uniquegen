package com.mrppa.uniquegen.generators.jdbcsequenceidgenerator;

import com.mrppa.uniquegen.BaseIDGeneratorTest;
import com.mrppa.uniquegen.ContextBuilder;
import com.mrppa.uniquegen.IDGenerator;
import com.mrppa.uniquegen.IDGeneratorContext;
import com.mrppa.uniquegen.generators.jdbcsequenceidgenerator.service.JDBCSequenceIDGenerator;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import javax.sql.DataSource;

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


    @Override
    public IDGenerator createIDGenerator() {
        IDGeneratorContext idGeneratorContext = new ContextBuilder()
                .add(JDBCSequenceIDGenerator.JDBC_DATASOURCE, dataSource)
                .add(JDBCSequenceIDGenerator.SEQUENCE_NAME, "test_Sequence")
                .build();
        return new JDBCSequenceIDGenerator(idGeneratorContext);
    }

}
