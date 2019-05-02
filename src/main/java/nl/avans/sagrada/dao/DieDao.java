package nl.avans.sagrada.dao;

import nl.avans.sagrada.database.DBConnection;

public class DieDao {
    private DBConnection dbConnection;

    /**
     * Constructor, Initializes DBConnection
     */
    public DieDao() {
        dbConnection = new DBConnection();
    }
}
