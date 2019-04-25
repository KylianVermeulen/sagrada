package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Player;

public class GameDAO {
    private DBConnection dbConnection;

    public GameDAO() {
        dbConnection = new DBConnection();
    }

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
            ResultSet rs = dbConnection.executeQuery(
                    new Query("UPDATE game SET turn_idplayer=? WHERE idgame=?", "update"),
                    new QueryParameter(QueryParameter.INT, turnPlayerId),
                    new QueryParameter(QueryParameter.INT, game.getId())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public ArrayList<Player> getPlayersOfGame(Game game) {
        PlayerDAO playerDAO = new PlayerDAO();
        ArrayList<Player> players = new ArrayList<>();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT idplayer FROM player WHERE game_idgame=?", "query"),
                    new QueryParameter(QueryParameter.INT, game.getId())
            );
            while (rs.next()) {
                int playerId = rs.getInt("idplayer");
                Player player = playerDAO.getPlayerById(playerId);
                player.setGame(game);
                players.add(player);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return players;
    }
}
