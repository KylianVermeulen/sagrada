package nl.avans.sagrada.dao;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Game;
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
     * Adds a gameDie and it's attributes to the database.
     *
     * @param gameId  int
     * @param gameDie GameDie
     */
    public void addDie(int gameId, GameDie gameDie, int round) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("INSERT INTO gamedie (idgame, dienumber, diecolor, eyes, round) VALUES (?, ?, ?, ?, ?)", "update"),
                    new QueryParameter(QueryParameter.INT, gameId),
                    new QueryParameter(QueryParameter.INT, gameDie.getNumber()),
                    new QueryParameter(QueryParameter.STRING, gameDie.getColor()),
                    new QueryParameter(QueryParameter.INT, gameDie.getEyes()),
                    new QueryParameter(QueryParameter.INT, round)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    new Query("UPDATE player SET patterncard_idpatterncard=? WHERE idplayer=?", "update"),

    public void updateDie(int gameId, GameDie gameDie, int round) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("UPDATE gamedie SET round=? WHERE idgame=? AND dienumber=? AND diecolor=?", "update"),
                    new QueryParameter(QueryParameter.INT, round),
                    new QueryParameter(QueryParameter.INT, gameId),
                    new QueryParameter(QueryParameter.INT, gameDie.getNumber()),
                    new QueryParameter(QueryParameter.STRING, gameDie.getColor())
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
                    new Query("SELECT * FROM gamedie WHERE idgame=?", "query"),
                    new QueryParameter(QueryParameter.INT, gameId)
            );
            while (rs.next()) {
                GameDie gameDie = new GameDie(rs.getInt("number"), rs.getString("color"), rs.getInt("eyes"));
                gameDice.add(gameDie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameDice;
    }

    public GameDie getDie(int gameId, GameDie die) {
        GameDie gameDie = null;
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM gamedie WHERE idgame=? AND dienumber=? AND diecolor=?", "query"),
                    new QueryParameter(QueryParameter.INT, gameId),
                    new QueryParameter(QueryParameter.INT, die.getNumber()),
                    new QueryParameter(QueryParameter.STRING, die.getColor())
            );
            while (rs.next()) {
                gameDie = new GameDie(rs.getInt("dienumber"), rs.getString("diecolor"), rs.getInt("eyes"), rs.getInt("round"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameDie;
    }

    public ArrayList<GameDie> getRoundDice(int gameId, int round) {
        ArrayList<GameDie> gameDice = new ArrayList<GameDie>();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM gamedie WHERE idgame=? AND round=?", "query"),
                    new QueryParameter(QueryParameter.INT, gameId),
                    new QueryParameter(QueryParameter.INT, round)
            );
            while (rs.next()) {
                GameDie gameDie = new GameDie(rs.getInt("dienumber"), rs.getString("diecolor"), rs.getInt("eyes"));
                gameDice.add(gameDie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameDice;
    }
}
