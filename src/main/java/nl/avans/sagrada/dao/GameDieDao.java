package nl.avans.sagrada.dao;

import nl.avans.sagrada.database.DBConnection;

public class GameDieDao {
    private DBConnection dbConnection;

    /**
     * Constructor, Initializes DBConnection
     */
    public GameDieDao() {
        dbConnection = new DBConnection();
    }
}
