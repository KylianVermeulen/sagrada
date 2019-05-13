package nl.avans.sagrada.dao;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.GameDie;

import java.sql.ResultSet;
import java.util.ArrayList;

public class GameDieDao {
    private DBConnection dbConnection;

    /**
     * Constructor, Initializes DBConnection
     */
    public GameDieDao() {
        dbConnection = new DBConnection();
    }

    /**
     * Adds a gameDie and it's attributes to the database.
     *
     * @param gameId int
     * @param gameDie GameDie
     */
    public void addDie(int gameId, GameDie gameDie) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("INSERT INTO gamedie (idgame, dienumber, diecolor, eyes) VALUES (?, ?, ?, ?)", "update"),
                    new QueryParameter(QueryParameter.INT, gameId),
                    new QueryParameter(QueryParameter.INT, gameDie.getNumber()),
                    new QueryParameter(QueryParameter.STRING, gameDie.getColor()),
                    new QueryParameter(QueryParameter.INT, gameDie.getEyes())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets all the dice from a game.
     *
     * @param gameId int
     * @return ArrayList<GameDie>
     */
    public ArrayList<GameDie> getDice(int gameId) {
        ArrayList<GameDie> gameDice = new ArrayList<GameDie>();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM gamedie WHERE gamedie_idgame=?", "query"),
                    new QueryParameter(QueryParameter.INT, gameId));
            while (rs.next()) {
                GameDie gameDie = new GameDie(rs.getInt("number"), rs.getString("color"), rs.getInt("eyes"));
                gameDice.add(gameDie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameDice;
    }
}
