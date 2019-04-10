package nl.avans.sagrada;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {
    private Connection conn = null;
    private Statement stmt = null;

    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://sagrada.samebestserver.nl:100/kylian.sagrada?serverTimezone=Europe/Amsterdam";

    private static final String USER = "sagrada";
    private static final String PASS = "sagrada";

    public DB() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void open() {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void close() {
        try {
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public ResultSet executeQuery(String sql) {
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public void executeUpdate(String sql) {
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
