package nl.avans.sagrada.dao;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Game;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameDAO {
    private DBConnection dbConnection;

    public Game getGameById(int gameId) {
        dbConnection = new DBConnection();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM game WHERE idgame=?", "query"),
                    new QueryParameter(QueryParameter.INT, gameId)
            );
            if (rs.next()) {
                Game game = new Game();
                game.setId(rs.getInt("idgame"));
                game.setTurnIdPlayer(rs.getInt("turn_idplayer"));
                return game;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateGame(Game game) {

    }

    public void addGame(Game game) {

    }
}
