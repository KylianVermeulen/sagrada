package nl.avans.sagrada.dao;

import nl.avans.sagrada.database.DBConnection;

public class GameDieDAO {
    private DBConnection dbConnection;

    public GameDieDAO() {
        dbConnection = new DBConnection();
    }
}
