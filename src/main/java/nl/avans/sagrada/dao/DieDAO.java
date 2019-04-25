package nl.avans.sagrada.dao;

import nl.avans.sagrada.database.DBConnection;

public class DieDAO {
    private DBConnection dbConnection;

    public DieDAO() {
        dbConnection = new DBConnection();
    }
}
