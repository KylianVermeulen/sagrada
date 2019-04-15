package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Player;

public class PlayerDAO {
    private DBConnection dbConnection;
    
    public PlayerDAO() {
        dbConnection = new DBConnection();
    }

    public ArrayList<Player> getPlayersOfAccount(Account account) {
        return null;
    }
    public void createNewPlayer(Player player, Game game) {

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
