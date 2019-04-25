package nl.avans.sagrada.dao;

import nl.avans.sagrada.database.DBConnection;

public class GameDieDAO {
    private DBConnection dbConnection;

    /**
     * Constructor, Initializes DBConnection
     */
    public GameDieDAO() {
        dbConnection = new DBConnection();
    }
}
