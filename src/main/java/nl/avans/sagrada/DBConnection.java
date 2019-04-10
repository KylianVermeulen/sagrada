package nl.avans.sagrada;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    private Connection connection = null;
    private String jdbc = null;
    private Properties connectionProperties = null;
    private List<HelperQuery> queuedQueries = new ArrayList<>();
    private List<HelperQuery> errorQueries = new ArrayList<>();
    private List<HelperQuery> executedQueries = new ArrayList<>();

    public DBConnection(String jdbc, Map<String, String> properties) {
        this.jdbc = jdbc;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            this.connectionProperties.put(entry.getKey(), entry.getValue());
        }
    }

    public DBConnection(String jdbc, Properties properties) {
        this.jdbc = jdbc;
        this.connectionProperties = properties;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(jdbc, getConnectionProperties());
            connection.setAutoCommit(false);
        }
        return connection;
    }

    public ResultSet executeQuery(HelperQuery helperQuery) throws SQLException {
        return executeQuery(helperQuery, 0);
    }

    public ResultSet executeQuery(HelperQuery helperQuery, QueryParameter... parameters) throws SQLException {
        PreparedStatement pstmt = prepareStatement(helperQuery.getSql());
        fillStatement(pstmt, parameters);
        return executeQueryHelper(helperQuery, pstmt);
    }

    public ResultSet executeQuery(HelperQuery helperQuery, int pos) throws SQLException {
        PreparedStatement pstmt = prepareStatement(helperQuery.getSql());
        fillStatement(pstmt, helperQuery.getParametersList().get(pos));
        return executeQueryHelper(helperQuery, pstmt);
    }

    private ResultSet executeQueryHelper(HelperQuery helperQuery, PreparedStatement pstmt) throws SQLException {
        ResultSet rs = null;
        try {
            if (helperQuery.getType().equals("update")) {
                executeUpdateStatement(pstmt);
            } else {
                rs = executeStatement(pstmt);
                helperQuery.setResultSet(rs);
            }
            queuedQueries.remove(helperQuery);
            executedQueries.add(helperQuery);
        } catch (SQLException e) {
            errorQueries.add(helperQuery);
            throw e;
        }
        return rs;
    }

    public ResultSet executeStatement(PreparedStatement pstmt) throws SQLException {
        ResultSet rs = pstmt.executeQuery();
        connection.commit();
        return rs;
    }

    public void executeUpdateStatement(PreparedStatement pstmt) throws SQLException {
        pstmt.executeUpdate();
        connection.commit();
    }

    public void fillStatement(PreparedStatement pstmt, QueryParameter... params) throws SQLException {
        for (int i = 1; i <= params.length; i++) {
            QueryParameter param = params[i - 1];
            Object value = param.getValue();
            switch (param.getType()) {
                case QueryParameter.BOOLEAN:
                    pstmt.setBoolean(i, (boolean) value);
                    break;
                case QueryParameter.DATE:
                    pstmt.setDate(i, (Date) value);
                    break;
                case QueryParameter.DOUBLE:
                    pstmt.setDouble(i, (double) value);
                    break;
                case QueryParameter.FLOAT:
                    pstmt.setFloat(i, (float) value);
                    break;
                case QueryParameter.INT:
                    pstmt.setInt(i, (int) value);
                    break;
                case QueryParameter.STRING:
                    pstmt.setString(i, (String) value);
                    break;
                case QueryParameter.TIME:
                    pstmt.setTime(i, (Time) value);
                    break;
                case QueryParameter.TIMESTAMP:
                    pstmt.setTimestamp(i, (Timestamp) value);
                    break;
            }
        }
    }

    public PreparedStatement prepareStatement(String query) throws SQLException {
        return getConnection().prepareStatement(query);
    }

    public List<HelperQuery> executeQueue() {
        List<HelperQuery> returnList = new ArrayList<>(queuedQueries.size());
        int i = 0;
        while (i < queuedQueries.size()) {
            HelperQuery helperQuery = queuedQueries.get(i);
            try {
                executeQuery(helperQuery);
                returnList.add(helperQuery);
            } catch (SQLException e) {
                i++; //If there's a failure move forward in the queue. If not, the query that ran will no longer be in the list.
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return returnList;
    }

    public void addQueryToQueue(HelperQuery helperQuery) {
        queuedQueries.add(helperQuery);
    }

    public String getJdbc() {
        return jdbc;
    }

    public void setJdbc(String jdbc) {
        this.jdbc = jdbc;
    }

    public List<HelperQuery> getQueuedQueries() {
        return queuedQueries;
    }

    public void setQueuedQueries(List<HelperQuery> queuedQueries) {
        this.queuedQueries = queuedQueries;
    }

    public List<HelperQuery> getErrorQueries() {
        return errorQueries;
    }

    public void setErrorQueries(List<HelperQuery> errorQueries) {
        this.errorQueries = errorQueries;
    }

    public List<HelperQuery> getExecutedQueries() {
        return executedQueries;
    }

    public void setExecutedQueries(List<HelperQuery> executedQueries) {
        this.executedQueries = executedQueries;
    }

    public Properties getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(Properties connectionProperties) {
        this.connectionProperties = connectionProperties;
    }
}
