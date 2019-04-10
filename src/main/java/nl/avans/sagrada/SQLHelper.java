package nl.avans.sagrada;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class SQLHelper {

    public static ResultSet executeQuery(String connectionUrl, Map<String, String> properties, String query, String type, QueryParameter... params) throws SQLException {
        return new DBConnection(connectionUrl, properties).executeQuery(new HelperQuery(query, type, params));
    }

    public static ResultSet executeQuery(DBConnection helperConnection, String query, String type, QueryParameter... params) throws SQLException {
        HelperQuery helperQuery = new HelperQuery(query, type, params);
        return helperConnection.executeQuery(helperQuery);
    }
}
