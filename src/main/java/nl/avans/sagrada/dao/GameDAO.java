package nl.avans.sagrada.dao;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Game;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameDAO {
    private DBConnection dbConnection;
    
    public GameDAO() {
        dbConnection = new DBConnection();
    }

    /**
     * Get game by gameid
     *
     * @param gameId int
     * @return Game when record
     */
    public Game getGameById(int gameId) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM game WHERE idgame=?", "query"),
                    new QueryParameter(QueryParameter.INT, gameId)
            );
            if (rs.next()) {
                Game game = new Game(rs.getInt("idgame"));
                return game;
            }
            System.out.println("No record for game with gameid: " + gameId);
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates a game
     * @param game
     */
    public void updateGame(Game game) {
        try {
            int turnPlayerId = game.getTurnPlayer().getId();
            ResultSet rs = dbConnection.executeQuery(new Query("UPDATE game SET turn_idplayer=? WHERE idgame=?", "update"),
                        new QueryParameter(QueryParameter.INT, turnPlayerId),
                        new QueryParameter(QueryParameter.INT, game.getId())
                    );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new game to the database
     * @param game
     * @return boolean
     */
    public void addGame(Game game) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                        new Query("INSERT INTO game (idgame) VALUES (?)", "update"),
                        new QueryParameter(QueryParameter.INT, game.getId())
                    );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Gets the next game id of a new game
     * @return int
     */
    public int getNextGameId() {
        int gameId = 0;
        try {
            ResultSet rs = dbConnection.executeQuery(
                        new Query("SELECT MAX(idgame) AS highestGameId FROM game", "query")
                    );
            if (rs.next()) {
                gameId = rs.getInt("highestGameId") + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameId;
    }
}
