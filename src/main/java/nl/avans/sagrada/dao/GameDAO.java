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

    public void updateGame(Game game) {
        try {
            int turnPlayerId = game.getTurnPlayer().getId();
            ResultSet rs = dbConnection.executeQuery(new Query("UPDATE game SET turn_idplayer=? WHERE idgame=?", "update"),
                        new QueryParameter(QueryParameter.INT, turnPlayerId),
                        new QueryParameter(QueryParameter.INT, game.getId())
                    );
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    public boolean addGame(Game game) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                        new Query("INSERT INTO game (idgame) VALUES (?)", "update"),
                        new QueryParameter(QueryParameter.INT, game.getId())
                    );
            return true;
        } catch (Exception e) {
            e.getStackTrace();
            return false;
        }
    }
    
    public Game createNewGame() {
        Game game = null;
        try {
            ResultSet rs = dbConnection.executeQuery(
                        new Query("SELECT MAX(idgame) AS highestGameId FROM game", "query")
                    );
            if (rs.next()) {
                int gameId = rs.getInt("highestGameId") + 1;
                game = new Game();
                game.setId(gameId);
                addGame(game);
                return game;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.getStackTrace();
        }
        return game;
    }
}
