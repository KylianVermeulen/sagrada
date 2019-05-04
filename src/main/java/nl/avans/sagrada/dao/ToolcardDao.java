package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Toolcard;

public class ToolcardDao {
    private DBConnection dbConnection;

    /**
     * Constructor, Initializes DBConnection
     */
    public ToolcardDao() {
        dbConnection = new DBConnection();
    }

    /**
     * Returns all toolcards that are stored in the database as entries, belonging to a certain game.
     * 
     * @param game The game to which the toolcards belong
     * @return An ArrayList of toolcards that belong to this game
     */
    public ArrayList<Toolcard> getToolcardsOfGame(Game game) {
        ArrayList<Toolcard> list = new ArrayList<>();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "SELECT toolcard.* FROM toolcard INNER JOIN gametoolcard g on toolcard.idtoolcard = g.idtoolcard WHERE g.idgame=?",
                            "query"),
                    new QueryParameter(QueryParameter.INT, game.getId())
            );
            while (rs.next()) {
                Toolcard toolcard = new Toolcard(rs.getInt("idtoolcard"), rs.getString("name"), rs.getInt("seqnr"),
                        rs.getString("description"));
                list.add(toolcard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Returns all toolcards that are stored in the database as entries.
     * 
     * @return An ArrayList containing all Toolcard entries from the database
     */
    public ArrayList<Toolcard> getAllToolcards() {
        ArrayList<Toolcard> list = new ArrayList<>();
        try {
            ResultSet rs = dbConnection.executeQuery(new Query("SELECT * FROM toolcard", "query"));
            while (rs.next()) {
                Toolcard toolcard = new Toolcard(rs.getInt("idtoolcard"),  rs.getString("name"), rs.getInt("seqnr"),
                        rs.getString("description"));
                list.add(toolcard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * Returns the toolcard that belongs to the given id.
     * 
     * @param id The to be returned toolcard's id
     * @return The toolcard that belongs to the id entered as parameter
     */
    public Toolcard getToolcardById(int id) {
        Toolcard toolcard = new Toolcard();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM toolcard WHERE idtoolcard=?", "query",
                            new QueryParameter(QueryParameter.INT, id)));
            if (rs.next()) {
                toolcard.setId(rs.getInt("idtoolcard"));
                toolcard.setName(rs.getString("name"));
                toolcard.setSeqnr(rs.getInt("seqnr"));
                toolcard.setDescription(rs.getString("description"));
            }
        } catch (Exception e) {
            toolcard = null;
            e.printStackTrace();
        }
        return toolcard;
    }

    /**
     * Adds a toolcard to a game. Both the toolcard and the game that are subject
     * to this method are given as parameters.
     * 
     * @param toolcard The Toolcard that should be added to a game
     * @param game The Game to which the toolcard should be added
     */
    public void addToolcardToGame(Toolcard toolcard, Game game) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "INSERT INTO gametoolcard (gametoolcard, idtoolcard, idgame) VALUES (?, ?, ?)", "update"
                            ),
                    new QueryParameter(QueryParameter.INT, getNextGameToolcardId()),
                    new QueryParameter(QueryParameter.INT, toolcard.getId()),
                    new QueryParameter(QueryParameter.INT, game.getId())
            );
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    /**
     * Method that returns the next gametoolcard integer that is available in the database
     * table 'gametoolcard'. This allows a new row to be added to the table, as this
     * method ensures that a possible new entry is added to an empty space in the table.
     * 
     * @return The next gametoolcard int, which has not yet been used by any existing
     * database entries
     */
    public int getNextGameToolcardId() {
        int gameToolcardId = 0;
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT MAX(gametoolcard) AS highestGameToolcardId FROM gametoolcard", "query")               
            );
            if (rs.next()) {
                gameToolcardId = rs.getInt("maxGameToolcard") + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gameToolcardId;
    }
}
