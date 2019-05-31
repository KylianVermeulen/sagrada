package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Player;

public class GameDao {
    private DBConnection dbConnection;

    /**
     * Constructor, Initializes DBConnection
     */
    public GameDao() {
        dbConnection = new DBConnection();
    }

    /**
     * This method will return a new game object using gameId as unique identifier.
     *
     * @param gameId The id from a game.
     * @return A new game object.
     */
    public Game getGameById(int gameId) {
        Game game = null;
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM game WHERE idgame=?", "query"),
                    new QueryParameter(QueryParameter.INT, gameId)
            );
            if (rs.next()) {
                game = new Game(rs.getInt("idgame"));
                game.setRound(getCurrentRound(game));
            } else {
                System.out.println("No record for game with gameid: " + gameId);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return game;
    }

    /**
     * This method will update a game in the database.
     *
     * @param game The game object to update.
     */
    public void updateGame(Game game) {
        try {
            int turnPlayerId = game.getTurnPlayer().getId();
            dbConnection.executeQuery(
                            new Query("UPDATE game SET turn_idplayer=? WHERE idgame=?", "update"),
                            new QueryParameter(QueryParameter.INT, turnPlayerId),
                            new QueryParameter(QueryParameter.INT, game.getId())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will add a game to the database.
     *
     * @param game The game object to add.
     */
    public void addGame(Game game) {
        try {
            dbConnection.executeQuery(
                            new Query(
                                    "INSERT INTO game (idgame, creationdate) VALUES (?, ?)",
                                    "update"),
                            new QueryParameter(QueryParameter.INT, game.getId()),
                            new QueryParameter(QueryParameter.TIMESTAMP, game.getCreationDate())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will return all games.
     *
     * @return games ArrayList<Game>
     */
    public ArrayList<Game> getAllGames() {
        ArrayList<Game> games = new ArrayList<Game>();
        try {
            ResultSet rs = dbConnection.executeQuery(new Query("SELECT * FROM game", "query"));
            while (rs.next()) {
                Game game = new Game(rs.getInt("idgame"));
                games.add(game);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return games;
    }

    /**
     * This method will return the next id for a new game.
     *
     * @return The next possible id in the database.
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
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameId;
    }

    /**
     * This method will return a list of players for a certain game given as parameter.
     *
     * @param game The game object to retrieve the players of.
     * @return A list of players.
     */
    public ArrayList<Player> getPlayersOfGame(Game game) {
        PlayerDao playerDao = new PlayerDao();
        ArrayList<Player> players = new ArrayList<>();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT idplayer FROM player WHERE game_idgame=?", "query"),
                    new QueryParameter(QueryParameter.INT, game.getId())
            );
            while (rs.next()) {
                int playerId = rs.getInt("idplayer");
                Player player = playerDao.getPlayerById(playerId);
                player.setGame(game);
                players.add(player);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return players;
    }

    public Timestamp getTime() {
        Timestamp timestamp = null;
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT NOW()", "query")
            );
            while (rs.next()) {
                timestamp = rs.getTimestamp("NOW()");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    public Player bestFinalScore(Game game) {
        Player player = null;
        try {
            ResultSet rs = dbConnection.executeQuery(new Query(
                    "SELECT idplayer, max(score) FROM player WHERE game_idgame=? GROUP BY idplayer ORDER BY score desc LIMIT 1",
                    "query"), 
                    new QueryParameter(QueryParameter.INT, game.getId())
            );
            if (rs.next()) {
                int playerId = rs.getInt("idplayer");
                player = new PlayerDao().getPlayerById(playerId);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return player;
    }

    /**
     * Gets the current round of a game
     * 
     * @param game
     * @return int
     */
    public int getCurrentRound(Game game) {
        int currentRound = 1;
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "SELECT DISTINCT(round) FROM gamedie WHERE idgame = ? AND roundtrack IS NOT NULL ORDER BY round DESC LIMIT 1",
                            "query"),
                    new QueryParameter(QueryParameter.INT, game.getId())
            );
            if (rs.next()) {
                currentRound = rs.getInt("round") + 1;
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentRound;
    }
}
