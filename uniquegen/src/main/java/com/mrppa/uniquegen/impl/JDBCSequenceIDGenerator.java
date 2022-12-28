package com.mrppa.uniquegen.impl;

import com.mrppa.uniquegen.IDGenerator;
import com.mrppa.uniquegen.IDGeneratorContext;
import com.mrppa.uniquegen.jdbc.BaseQueryTranslator;

import java.sql.*;

public class JDBCSequenceIDGenerator extends IDGenerator {
    public static final String JDBC_CONNECTION = "JDBC_CONNECTION";
    public static final String SEQUENCE_NAME = "SEQUENCE_NAME";

    private final Connection connection;
    private final String sequenceName;

    private final BaseQueryTranslator baseQueryTranslator;

    public JDBCSequenceIDGenerator(IDGeneratorContext idGeneratorContext) {
        super(idGeneratorContext);
        if (!idGeneratorContext.checkVariableAvailability(JDBC_CONNECTION)) {
            throw new RuntimeException(JDBC_CONNECTION + " Required ");
        }
        connection = idGeneratorContext.getFromContext(JDBC_CONNECTION, Connection.class, null);
        sequenceName = idGeneratorContext.getFromContext(SEQUENCE_NAME, String.class, "uniquegen_jdbc");

        baseQueryTranslator = BaseQueryTranslator.findMatchingTranslator(connection);

        try {
            initSequence();
        } catch (SQLException e) {
            throw new RuntimeException("Error while creating jdbc sequence", e);
        }
    }

    private void initSequence() throws SQLException {
        Statement st = connection.createStatement();
        st.executeUpdate(baseQueryTranslator.generateCreateSequenceScript(sequenceName));
    }

    @Override
    public String generateId() {
        try {
            Integer seqValue = getSequenceFromDB();
            if (seqValue != null) {
                return seqValue.toString();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching sequenceNumber from database", e);
        }
        throw new RuntimeException("Error in JDBC sequence");
    }

    private Integer getSequenceFromDB() throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                baseQueryTranslator.generateSequenceNextValScript(sequenceName));
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return null;
    }
}