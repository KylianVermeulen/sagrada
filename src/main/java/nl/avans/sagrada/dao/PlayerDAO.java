package nl.avans.sagrada.dao;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlayerDAO {
    private DBConnection dbConnection;
    
    public PlayerDAO() {
        dbConnection = new DBConnection();
    }

    /**
     * Get all players with account.username
     *
     * @param account Account
     * @return ArrayList<Player> when record(s)
     */
    public ArrayList<Player> getPlayersOfAccount(Account account) {
       ArrayList<Player> list = new ArrayList<Player>();
       try {
           ResultSet rs = dbConnection.executeQuery(
                   new Query("SELECT * FROM player WHERE username=?", "query",
                   new QueryParameter(QueryParameter.STRING, account.getUsername()))
           );
           while (rs.next()) {
               Player player = new Player();
               player.setId(rs.getInt("idplayer"));
               player.setAccount(account);
               player.setGame(new GameDAO().getGameById(rs.getInt("game_idgame")));
               player.setPlayerStatus(rs.getString("playstatus_playstatus"));
               player.setSeqnr(rs.getInt("seqnr"));
               player.setCurrentPlayer(rs.getBoolean("isCurrentPlayer"));
               player.setPrivateObjectivecardColor(rs.getString("private_objectivecard_color"));
               player.setScore(rs.getInt("score"));
               list.add(player);
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return list;
    }

    /**
     * Update player
     *
     * @param player Player
     */
    public void updatePlayer(Player player) {
        if (playerExists(player)) {
            try {
                ResultSet rs = dbConnection.executeQuery(
                        new Query("UPDATE player SET username=?, game_idgame=?, playstatus_playstatus=?, seqnr=?, isCurrentPlayer=?, private_objectivecard_color=?, score=? WHERE idplayer=?", "update"),
                        new QueryParameter(QueryParameter.STRING, player.getAccount().getUsername()),
                        new QueryParameter(QueryParameter.INT, player.getGame().getId()),
                        new QueryParameter(QueryParameter.STRING, player.getPlayerStatus()),
                        new QueryParameter(QueryParameter.INT, player.getSeqnr()),
                        new QueryParameter(QueryParameter.BOOLEAN, player.isCurrentPlayer()),
                        new QueryParameter(QueryParameter.STRING, player.getPrivateObjectivecardColor()),
                        new QueryParameter(QueryParameter.INT, player.getScore()),
                        new QueryParameter(QueryParameter.INT, player.getId())
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Can't update non existing player");
        }
    }

    /**
     * Add player
     *
     * @param player Player
     */
    public void addPlayer(Player player) {
        if (!playerExists(player)) {
            try {
                ResultSet rs = dbConnection.executeQuery(
                        new Query("INSERT INTO player (idplayer, username, game_idgame, playstatus_playstatus, seqnr, isCurrentPlayer, private_objectivecard_color, score) VALUES (?,?, ?, ?, ?, ?, ?, ?)", "update"),
                        new QueryParameter(QueryParameter.INT, player.getId()),
                        new QueryParameter(QueryParameter.STRING, player.getAccount().getUsername()),
                        new QueryParameter(QueryParameter.INT, player.getGame().getId()),
                        new QueryParameter(QueryParameter.STRING, player.getPlayerStatus()),
                        new QueryParameter(QueryParameter.INT, player.getSeqnr()),
                        new QueryParameter(QueryParameter.BOOLEAN, player.isCurrentPlayer()),
                        new QueryParameter(QueryParameter.STRING, player.getPrivateObjectivecardColor()),
                        new QueryParameter(QueryParameter.INT, player.getScore())
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Can't create a new player, already exists");
        }
    }

    /**
     * Check if player exists
     *
     * @param player Player
     * @return boolean true when exists
     */
    public boolean playerExists(Player player) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT count(*) as count FROM player WHERE idplayer=?", "query"),
                    new QueryParameter(QueryParameter.INT, player.getId())
            );
            if (rs.next()) {
                int count = rs.getInt("count");
                if (count > 0) {
                    return  true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Gets a player by the Id of the player
     * When no player has found it will return null
     * @param id the id of the player
     * @return Player
     */
    public Player getPlayerById(int id) {
        Player player = new Player();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM player WHERE idplayer=?", "query",
                            new QueryParameter(QueryParameter.INT, id)));
            if (rs.next()) {
                AccountDAO accountDao = new AccountDAO();
                Account account = accountDao.getAccountByUsername(rs.getString("username"));
                player.setId(rs.getInt("idplayer"));
                player.setPlayerStatus(rs.getString("playstatus_playstatus"));
                player.setSeqnr(rs.getInt("seqnr"));
                player.setIsCurrentPlayer(rs.getBoolean("isCurrentPlayer"));
                player.setAccount(account);
            }
        } catch (Exception e) {
            // TODO: handle exception
            player = null;
        }
        return player;
    }
    
    /**
     * Gets the next id for a player
     * @return int
     */
    public int getNextPlayerId() {
        try {
            ResultSet rs = dbConnection.executeQuery(new Query("SELECT MAX(idplayer) AS highestId FROM player", "query"));
            if (rs.next()) {
                int nextId = rs.getInt("highestId") + 1;
                return nextId;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.getStackTrace();
        }
        return 0;
    }

    /**
     * Gets a Player by the account and game
     * @param account
     * @param game
     * @return Player
     */
    public Player getPlayerByAccountAndGame(Account account, Game game) {
        Player player = null;
        try {
            ResultSet rs = dbConnection.executeQuery(new Query("SELECT idplayer FROM player WHERE username=? AND game_idgame=?", "query"), 
                        new QueryParameter(QueryParameter.STRING, account.getUsername()),
                        new QueryParameter(QueryParameter.INT, game.getId())
                    );
            if (rs.next()) {
                System.out.println("Done");
                int playerId = rs.getInt("idplayer");
                System.out.println("playerid: " + playerId);
                player = getPlayerById(playerId);
            }
        } catch (Exception e) {
        }
        return player;
    }
}
