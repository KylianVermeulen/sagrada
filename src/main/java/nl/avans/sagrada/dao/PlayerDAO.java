package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Player;

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
       dbConnection = new DBConnection();
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
               player.setPatternCard(new PatterncardDAO().getPatterncardOfPlayer(player));
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
        dbConnection = new DBConnection();
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
        dbConnection = new DBConnection();
        if (!playerExists(player)) {
            try {
                ResultSet rs = dbConnection.executeQuery(
                        new Query("INSERT INTO player (username, game_idgame, playstatus_playstatus, seqnr, isCurrentPlayer, private_objectivecard_color, score) VALUES (?, ?, ?, ?, ?, ?, ?)", "update"),
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
        dbConnection = new DBConnection();
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
                player.setId(rs.getInt("idplayer"));
                player.setPlayerStatus(rs.getString("playstatus_playstatus"));
                player.setSeqnr(rs.getInt("seqnr"));
                player.setIsCurrentPlayer(rs.getBoolean("isCurrentPlayer"));
                player.setAccount(new Account(rs.getString("username")));
            }
        } catch (Exception e) {
            // TODO: handle exception
            player = null;
        }
        return player;
    }
}
