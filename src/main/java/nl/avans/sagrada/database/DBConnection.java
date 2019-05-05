package nl.avans.sagrada.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    private static Connection connection = null;
    private static String devDatabaseUrl = "jdbc:mysql://134.209.204.60:3306/sagrada_kylian?serverTimezone=Europe/Amsterdam";
    private static String dbPassword = "Sagrada1!";
    private static String dbUser = "sagrada";
    private Properties connectionProperties;
    private List<Query> queuedQueries = new ArrayList<>();
    private List<Query> errorQueries = new ArrayList<>();
    private List<Query> executedQueries = new ArrayList<>();

    /**
     * Empty  constructor
     */
    public DBConnection() {
        connectionProperties = new Properties();
        connectionProperties.put("user", dbUser);
        connectionProperties.put("password", dbPassword);
    }

    public static String getDevDatabaseUrl() {
        return devDatabaseUrl;
    }

    public static void setDevDatabaseUrl(String devDatabaseUrl) {
        DBConnection.devDatabaseUrl = devDatabaseUrl;
    }

    public static String getDbPassword() {
        return dbPassword;
    }

    public static void setDbPassword(String dbPassword) {
        DBConnection.dbPassword = dbPassword;
    }

    public static String getDbUser() {
        return dbUser;
    }

    public static void setDbUser(String dbUser) {
        DBConnection.dbUser = dbUser;
    }

    /**
     * Returns a open connection, make a new connection when conn == null or isClosed
     *
     * @return new Connection
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(devDatabaseUrl, getConnectionProperties());
            connection.setAutoCommit(false);
        }
        return connection;
    }

    /**
     * Executes the given Query on this connection based on Query sql and Query first
     * QueryParameter
     *
     * @param query Query
     * @return ResultSet
     * @throws SQLException SQLException
     */
    public ResultSet executeQuery(Query query) throws SQLException {
        return executeQuery(query, 0);
    }

    /**
     * Executes the given Query on this connection based on Query sql and given QueryParameters
     *
     * @param query Query
     * @param parameters QueryParameter...
     * @return ResultSet
     * @throws SQLException SQLException
     */
    public ResultSet executeQuery(Query query, QueryParameter... parameters) throws SQLException {
        PreparedStatement pstmt = prepareStatement(query.getSql());
        fillStatement(pstmt, parameters);
        return executeQueryHelper(query, pstmt);
    }

    /**
     * Executes the given Query on this connection based on Query sql and Query QueryParameter on
     * pos
     *
     * @param query Query
     * @param pos QueryParameter array position in Query
     * @return ResultSet
     * @throws SQLException SQLException
     */
    public ResultSet executeQuery(Query query, int pos) throws SQLException {
        PreparedStatement pstmt = prepareStatement(query.getSql());
        fillStatement(pstmt, query.getParametersList().get(pos));
        return executeQueryHelper(query, pstmt);
    }

    /**
     * Used as dependency for the executeQuery methods
     *
     * @param query Query
     * @param pstmt PreparedStatement
     * @return ResultSet
     * @throws SQLException SQLException
     */
    private ResultSet executeQueryHelper(Query query, PreparedStatement pstmt) throws SQLException {
        ResultSet rs = null;
        try {
            if (query.getType().equals("update")) {
                executeUpdateStatement(pstmt);
            } else {
                rs = executeStatement(pstmt);
                query.setResultSet(rs);
            }
            queuedQueries.remove(query);
            executedQueries.add(query);
        } catch (SQLException e) {
            errorQueries.add(query);
            throw e;
        }
        return rs;
    }

    /**
     * Executes the given statement (select) and returns result set
     *
     * @param pstmt PreparedStatement
     * @return ResultSet
     * @throws SQLException SQLException
     */
    public ResultSet executeStatement(PreparedStatement pstmt) throws SQLException {
        ResultSet rs = pstmt.executeQuery();
        connection.commit();
        return rs;
    }

    /**
     * Executes the given update statement (insert, update, delete)
     *
     * @param pstmt PreparedStatement
     * @throws SQLException SQLException
     */
    public void executeUpdateStatement(PreparedStatement pstmt) throws SQLException {
        pstmt.executeUpdate();
        connection.commit();
    }

    /**
     * Apply QueryParameters to PreparedStatement
     *
     * @param pstmt PreparedStatement
     * @param params QueryParameter...
     * @throws SQLException SQLException
     */
    public void fillStatement(PreparedStatement pstmt, QueryParameter... params)
            throws SQLException {
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

    /**
     * Gets (and creates) a connection and returns a PreparedStatement
     *
     * @param query sql
     * @return PreparedStatement
     * @throws SQLException SQLException
     */
    public PreparedStatement prepareStatement(String query) throws SQLException {
        return getConnection().prepareStatement(query);
    }

    public List<Query> executeQueue() {
        List<Query> returnList = new ArrayList<>(queuedQueries.size());
        int i = 0;
        while (i < queuedQueries.size()) {
            Query query = queuedQueries.get(i);
            try {
                executeQuery(query);
                returnList.add(query);
            } catch (SQLException e) {
                i++; //If there's a failure move forward in the queue. If not, the query that ran will no longer be in the list.
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return returnList;
    }

    /**
     * Add a Query to execute queue
     *
     * @param query Query
     */
    public void addQueryToQueue(Query query) {
        queuedQueries.add(query);
    }

    public List<Query> getQueuedQueries() {
        return queuedQueries;
    }

    public void setQueuedQueries(List<Query> queuedQueries) {
        this.queuedQueries = queuedQueries;
    }

    public List<Query> getErrorQueries() {
        return errorQueries;
    }

    public void setErrorQueries(List<Query> errorQueries) {
        this.errorQueries = errorQueries;
    }

    public List<Query> getExecutedQueries() {
        return executedQueries;
    }

    public void setExecutedQueries(List<Query> executedQueries) {
        this.executedQueries = executedQueries;
    }

    public Properties getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(Properties connectionProperties) {
        this.connectionProperties = connectionProperties;
    }
}
