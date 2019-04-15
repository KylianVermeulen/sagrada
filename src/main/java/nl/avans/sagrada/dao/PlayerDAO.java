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

    public ArrayList<Player> getPlayersOfAccount(Account account) {
       dbConnection = new DBConnection();
       try {
           ResultSet rs = dbConnection.executeQuery(
                   new Query("SELECT * FROM player WHERE username=?", "query",
                   new QueryParameter(QueryParameter.STRING, account.getUsername()))
           );
           ArrayList<Player> list = new ArrayList<Player>();
           while (rs.next()) {
               Player player = new Player(rs.getInt("idplayer"), rs.getString("username"),
                       rs.getInt("game_idgame"), rs.getString("playstatus_playstatus"),
                       rs.getInt("seqnr"), rs.getBoolean("isCurrentPlayer"),
                       rs.getString("private_objectivecard_color"), rs.getInt("patterncard_idpatterncard"),
                       rs.getInt("score"), account);
               list.add(player);
           }
           return list;
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return null;
    }

    public void updatePlayer(Player player) {
        dbConnection = new DBConnection();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("UPDATE player SET username=?, game_idgamee=?, playstatus_playstatus=?, seqnr=?, isCurrentPlayer=?, private_objectivecard_color=?, patterncard_idpatterncard=?, score=? WHERE idplayer=?", "update"),
                    new QueryParameter(QueryParameter.STRING, player.getUsername()),
                    new QueryParameter(QueryParameter.INT, player.getIdgame()),
                    new QueryParameter(QueryParameter.STRING, player.getPlayerStatus()),
                    new QueryParameter(QueryParameter.INT, player.getSeqnr()),
                    new QueryParameter(QueryParameter.BOOLEAN, player.isCurrentPlayer()),
                    new QueryParameter(QueryParameter.STRING, player.getPrivateObjectivecardColor()),
                    new QueryParameter(QueryParameter.STRING, player.getIdPatterncard()),
                    new QueryParameter(QueryParameter.INT, player.getScore()),
                    new QueryParameter(QueryParameter.INT, player.getId())
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPlayer(Player player) {
        dbConnection = new DBConnection();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("INSERT INTO player (username, game_idgame, playstatus_playstatus, seqnr, isCurrentPlayer, private_objectivecard_color, patterncard_idpatterncard, score) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", "update"),
                    new QueryParameter(QueryParameter.STRING, player.getUsername()),
                    new QueryParameter(QueryParameter.INT, player.getIdgame()),
                    new QueryParameter(QueryParameter.STRING, player.getPlayerStatus()),
                    new QueryParameter(QueryParameter.INT, player.getSeqnr()),
                    new QueryParameter(QueryParameter.BOOLEAN, player.isCurrentPlayer()),
                    new QueryParameter(QueryParameter.STRING, player.getPrivateObjectivecardColor()),
                    new QueryParameter(QueryParameter.STRING, player.getIdPatterncard()),
                    new QueryParameter(QueryParameter.INT, player.getScore())
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
