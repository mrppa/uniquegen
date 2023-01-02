package com.mrppa.uniquegen.generators.jdbctablesequenceidgenerator.service;

import com.mrppa.uniquegen.IDGenerator;
import com.mrppa.uniquegen.IDGeneratorContext;
import com.mrppa.uniquegen.exception.IDGeneratorException;
import com.mrppa.uniquegen.jdbc.BaseQueryTranslator;
import com.mrppa.uniquegen.model.GenerateType;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class JDBCTableSequenceIDGenerator extends IDGenerator {
    private static final Logger logger = Logger.getLogger(JDBCTableSequenceIDGenerator.class.getName());
    public static final String JDBC_DATASOURCE = "JDBC_DATASOURCE";
    public static final String SEQUENCE_NAME = "SEQUENCE_NAME";
    public static final String CACHE_SIZE = "CACHE_SIZE";
    private final String sequenceName;
    private final BaseQueryTranslator baseQueryTranslator;
    private final DataSource dataSource;
    private final int cacheSize;
    private final BlockingQueue<String> localIDQueue;


    public JDBCTableSequenceIDGenerator(IDGeneratorContext idGeneratorContext) {
        super(idGeneratorContext);
        dataSource = idGeneratorContext.getFromContext(JDBC_DATASOURCE, DataSource.class, null);
        sequenceName = idGeneratorContext.getFromContext(SEQUENCE_NAME, String.class, "uniquegen_seq");
        cacheSize = idGeneratorContext.getFromContext(CACHE_SIZE, Integer.class, 100);
        localIDQueue = new ArrayBlockingQueue<>(cacheSize);

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            baseQueryTranslator = BaseQueryTranslator.findMatchingTranslator(connection);
            initSequence(connection);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error while creating jdbc sequence table", e);
        }
        scheduleDataFetch();
    }

    @Override
    public GenerateType getGenerateType() {
        return GenerateType.JDBC_TABLE_SEQUENCE_BASED;
    }

    private void initSequence(Connection connection) throws SQLException {
        try (Statement st = connection.createStatement()) {
            st.executeUpdate(baseQueryTranslator.generateCreateSequenceTableScript());
        }

        try (PreparedStatement ps = connection.prepareStatement(baseQueryTranslator.generateFetchTableRecordSequenceValue(false))) {
            ps.setString(1, sequenceName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return;
            }
        }
        try (Statement st = connection.createStatement()) {
            st.executeUpdate(baseQueryTranslator.generateInsertRecordToSequenceTableScript(sequenceName));
        }
    }

    @Override
    public List<String> generateIds(int numberOfIds) {
        return IntStream.range(0, numberOfIds).mapToObj(i ->
        {
            try {
                return localIDQueue.poll(60, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new IDGeneratorException("Timeout while retrieving ids");
            }
        }).toList();
    }

    private void scheduleDataFetch() {
        Runnable dataRetrievalThread = () -> {
            while (true) {
                try {
                    List<String> fetchedIds = JDBCTableSequenceIDGenerator.this.fetchIdsSequenceFromDB();
                    for (String id : fetchedIds) {
                        localIDQueue.offer(id, 365, TimeUnit.DAYS);
                    }
                } catch (SQLException e) {
                    logger.log(Level.WARNING, "Error fetching data from the database", e);
                } catch (InterruptedException e) {
                    logger.log(Level.WARNING, "Timeout while queuing data");
                }

            }
        };
        Thread t1 = new Thread(dataRetrievalThread);
        t1.setName("JDBCTableSequenceIDGenerator-DataRetrieval");
        t1.start();
    }

    private List<String> fetchIdsSequenceFromDB() throws SQLException {
        logger.info("Fetching data from the database");
        String nextValue = null;
        List<String> temporaryIdList = new ArrayList<>();

        String selectQuery = baseQueryTranslator.generateFetchTableRecordSequenceValue(true);
        String updateQuery = baseQueryTranslator.generateUpdateTableRecordSequenceValue();
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement ps = connection.prepareStatement(selectQuery)) {
                ps.setString(1, sequenceName);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    nextValue = rs.getString("next_val");
                }
            }
            logger.info("Fetching  nextValue for the sequence" + sequenceName + " is " + nextValue);
            if (nextValue != null) {
                for (int i = 0; i < cacheSize; i++) {
                    String id = StringUtils.leftPad(nextValue, 20, "0");
                    temporaryIdList.add(id);
                    nextValue = new BigInteger(nextValue).add(BigInteger.ONE).toString();
                }
                logger.info("Updating the nextValue for the sequence" + sequenceName + " as " + nextValue);
                try (PreparedStatement ps = connection.prepareStatement(updateQuery)) {
                    ps.setString(1, nextValue);
                    ps.setString(2, sequenceName);
                    ps.executeUpdate();
                }
            }
            connection.commit();
        }
        return temporaryIdList;
    }
}
