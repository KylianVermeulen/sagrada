package nl.avans.sagrada.dao;

import nl.avans.sagrada.database.DBConnection;

public class DieDAO {
    private DBConnection dbConnection;

    /**
     * Constructor, Initializes DBConnection
     */
    public DieDAO() {
        dbConnection = new DBConnection();
    }
}
