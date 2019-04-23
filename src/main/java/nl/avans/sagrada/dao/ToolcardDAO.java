package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Toolcard;

public class ToolcardDAO {
    private DBConnection dbConnection;
    
    public ToolcardDAO() {
        dbConnection = new DBConnection();
    }

    /**
     * Returns all toolcards that are active during the selected game.
     * @param game Game
     * @return an ArrayList (type: Toolcard) of all toolcards that are active in the selected game
     */
    public ArrayList<Toolcard> getToolcardsOfGame(Game game) {
        ArrayList<Toolcard> list = new ArrayList<Toolcard>();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT toolcard.* FROM toolcard INNER JOIN gametoolcard g on toolcard.idtoolcard = g.idtoolcard WHERE g.idgame=?" , "query"),
                    new QueryParameter(QueryParameter.INT, game.getId())
            );
            while (rs.next()) {
                Toolcard toolcard = new Toolcard(rs.getInt("idtoolcard"), rs.getInt("seqnr"), rs.getString("description"));
                list.add(toolcard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * Returns all toolcards that are stored in the database.
     * @return an ArrayList (type: Toolcard) of all toolcards stored within the database
     */
    public ArrayList<Toolcard> getAllToolcards() {
        ArrayList<Toolcard> list = new ArrayList<Toolcard>();
        try {
            ResultSet rs = dbConnection.executeQuery(new Query("SELECT * FROM toolcard", "query"));
            while (rs.next()) {
                Toolcard toolcard = new Toolcard(rs.getInt("idtoolcard"), rs.getInt("seqnr"), rs.getString("description"));
                list.add(toolcard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
