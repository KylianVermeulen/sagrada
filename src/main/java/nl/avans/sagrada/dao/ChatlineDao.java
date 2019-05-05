package nl.avans.sagrada.dao;

import nl.avans.sagrada.database.DBConnection;

public class ChatlineDao {
    private DBConnection dbConnection;

    /**
     * Constructor, Initializes DBConnection
     */
    public ChatlineDao() {
        dbConnection = new DBConnection();
    }
}
