package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Player;

public class PlayerDAO {
    private DBConnection dbConnection;

    public ArrayList<Player> getPlayersOfAccount(Account account) {
       dbConnection = new DBConnection();
       try {
           ResultSet rs = dbConnection.executeQuery(
                   new Query("SELECT * FROM player WHERE username=?", "query",
                   new QueryParameter(QueryParameter.STRING, account.getUsername()))
           );
           ArrayList<Player> list = new ArrayList<Player>();
           while (rs.next()) {
               Player player = new Player();
               player.setId(rs.getInt("idplayer"));
               player.setUsername(rs.getString("username"));
               player.setIdGame(rs.getInt("game_idgame"));
               player.setPlayerStatus(rs.getString("playstatus_playstatus"));
               player.setSeqnr(rs.getInt("seqnr"));
               player.setCurrentPlayer(rs.getBoolean("isCurrentPlayer"));
               player.setPrivateObjectivecardColor(rs.getString("private_objectivecard_color"));
               player.setIdPatterncard(rs.getString("patterncard_idpatterncard"));
               player.setScore(rs.getInt("score"));
               list.add(player);
           }
           return list;
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return null;
    }

    public void createNewPlayer(Player player, Game game) {

    }
}
