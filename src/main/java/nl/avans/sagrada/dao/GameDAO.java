package nl.avans.sagrada.dao;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Game;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameDAO {
    private DBConnection dbConnection;

    /**
     * Get game by gameid
     *
     * @param gameId int
     * @return Game when record
     */
    public Game getGameById(int gameId) {
        dbConnection = new DBConnection();
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
    }

    public void addGame(Game game) {
    }
}
