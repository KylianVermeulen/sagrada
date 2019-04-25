package nl.avans.sagrada.dao;

import nl.avans.sagrada.database.DBConnection;

public class ChatlineDAO {
    private DBConnection dbConnection;

    /**
     * Constructor, Initializes DBConnection
     */
    public ChatlineDAO() {
        dbConnection = new DBConnection();
    }
}
