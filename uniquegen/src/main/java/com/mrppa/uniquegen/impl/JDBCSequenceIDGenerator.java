package com.mrppa.uniquegen.impl;

import com.mrppa.uniquegen.IDGenerator;
import com.mrppa.uniquegen.IDGeneratorContext;
import com.mrppa.uniquegen.jdbc.BaseQueryTranslator;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JDBCSequenceIDGenerator extends IDGenerator {
    public static final String JDBC_DATASOURCE = "JDBC_DATASOURCE";
    public static final String SEQUENCE_NAME = "SEQUENCE_NAME";

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private final String sequenceName;
    private final BaseQueryTranslator baseQueryTranslator;
    private final DataSource dataSource;

    public JDBCSequenceIDGenerator(IDGeneratorContext idGeneratorContext) {
        super(idGeneratorContext);
        if (!idGeneratorContext.checkVariableAvailability(JDBC_DATASOURCE)) {
            throw new RuntimeException(JDBC_DATASOURCE + " Required ");
        }
        dataSource = idGeneratorContext.getFromContext(JDBC_DATASOURCE, DataSource.class, null);
        sequenceName = idGeneratorContext.getFromContext(SEQUENCE_NAME, String.class, "uniquegen_jdbc");

        try (Connection connection = dataSource.getConnection()) {
            baseQueryTranslator = BaseQueryTranslator.findMatchingTranslator(connection);
            initSequence(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Error while creating jdbc sequence", e);
        }
    }

    private void initSequence(Connection connection) throws SQLException {
        try (Statement st = connection.createStatement()) {
            st.executeUpdate(baseQueryTranslator.generateCreateSequenceScript(sequenceName));
        }
    }

    @Override
    public String generateId() {
        String dateComponent = LocalDateTime.now().format(dateFormat);
        String sequenceComponent;
        try {
            Integer seqValue = getSequenceFromDB();
            if (seqValue == null) {
                throw new RuntimeException("Error in JDBC sequence");
            }
            sequenceComponent = StringUtils.leftPad(seqValue.toString(), 10, "0");
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching sequenceNumber from database", e);
        }
        return String.format("%s%s", dateComponent, sequenceComponent);


    }

    private Integer getSequenceFromDB() throws SQLException {
        String sqlQuery = baseQueryTranslator.generateSequenceNextValScript(sequenceName);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return null;
    }
}
