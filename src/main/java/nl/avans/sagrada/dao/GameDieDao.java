package nl.avans.sagrada.dao;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.GameDie;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.model.PatternCardField;

public class GameDieDao {
    private DBConnection dbConnection;

    /**
     * Constructor, Initializes DBConnection
     */
    public GameDieDao() {
        dbConnection = new DBConnection();
    }

    /**
     * Adds gamedie to game in the database
     *
     * @param game Game
     * @param gameDie GameDie
     */
    public void addDie(Game game, GameDie gameDie) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "INSERT INTO gamedie (idgame, dienumber, diecolor, eyes) VALUES (?, ?, ?, ?)",
                            "update"),
                    new QueryParameter(QueryParameter.INT, game.getId()),
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
     * @param game Game
     * @param gameDie GameDie
     */
    public void addDie(Game game, GameDie gameDie, int round) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "INSERT INTO gamedie (idgame, dienumber, diecolor, eyes, round) VALUES (?, ?, ?, ?, ?)",
                            "update"),
                    new QueryParameter(QueryParameter.INT, game.getId()),
                    new QueryParameter(QueryParameter.INT, gameDie.getNumber()),
                    new QueryParameter(QueryParameter.STRING, gameDie.getColor()),
                    new QueryParameter(QueryParameter.INT, gameDie.getEyes()),
                    new QueryParameter(QueryParameter.INT, round)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the die with a given round
     *
     * @param game Game
     * @param gameDie GameDie
     * @param round int
     */
    public void updateDie(Game game, GameDie gameDie, int round) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "UPDATE gamedie SET round=? WHERE idgame=? AND dienumber=? AND diecolor=?",
                            "update"),
                    new QueryParameter(QueryParameter.INT, round),
                    new QueryParameter(QueryParameter.INT, game.getId()),
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
     * @param game Game
     * @return ArrayList<GameDie>
     */
    public ArrayList<GameDie> getDiceFromGame(Game game) {
        ArrayList<GameDie> gameDice = new ArrayList<GameDie>();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM gamedie WHERE idgame=?", "query"),
                    new QueryParameter(QueryParameter.INT, game.getId())
            );
            while (rs.next()) {
                GameDie gameDie = new GameDie(rs.getInt("number"), rs.getString("color"),
                        rs.getInt("eyes"));
                gameDice.add(gameDie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameDice;
    }

    /**
     * Gets the die from a game
     *
     * @param game Game
     * @param die GameDie
     * @return GameDie
     */
    public GameDie getDie(Game game, GameDie die) {
        GameDie gameDie = null;
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM gamedie WHERE idgame=? AND dienumber=? AND diecolor=?",
                            "query"),
                    new QueryParameter(QueryParameter.INT, game.getId()),
                    new QueryParameter(QueryParameter.INT, die.getNumber()),
                    new QueryParameter(QueryParameter.STRING, die.getColor())
            );
            while (rs.next()) {
                gameDie = new GameDie(rs.getInt("dienumber"), rs.getString("diecolor"),
                        rs.getInt("eyes"), rs.getInt("round"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameDie;
    }

    /**
     * Gets the dice for a round from a game
     *
     * @param game Game
     * @param round int
     * @return ArrayList<GameDie>
     */
    public ArrayList<GameDie> getRoundDices(Game game, int round) {
        ArrayList<GameDie> gameDice = new ArrayList<GameDie>();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM gamedie WHERE idgame=? AND round=?", "query"),
                    new QueryParameter(QueryParameter.INT, game.getId()),
                    new QueryParameter(QueryParameter.INT, round)
            );
            while (rs.next()) {
                GameDie gameDie = new GameDie(rs.getInt("dienumber"), rs.getString("diecolor"),
                        rs.getInt("eyes"));
                gameDice.add(gameDie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameDice;
    }

    public void placeDie(GameDie die, PatternCardField patterncardfield, Player player) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "UPDATE playerframefield SET dienumber=?, diecolor=? WHERE player_idplayer=? AND position_y=? AND position_x=? AND idgame=? ",
                            "update"),
                    new QueryParameter(QueryParameter.INT, die.getNumber()),
                    new QueryParameter(QueryParameter.STRING, die.getColor()),
                    new QueryParameter(QueryParameter.INT, player.getGame().getId()),
                    new QueryParameter(QueryParameter.INT, patterncardfield.getyPos()),
                    new QueryParameter(QueryParameter.INT, patterncardfield.getxPos()),
                    new QueryParameter(QueryParameter.INT, player.getGame().getId())
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
