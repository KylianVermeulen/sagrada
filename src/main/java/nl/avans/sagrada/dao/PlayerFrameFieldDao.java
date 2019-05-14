package nl.avans.sagrada.dao;

import java.sql.ResultSet;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;

public class PlayerFrameFieldDao {
    private DBConnection dbConnection;
    
    public PlayerFrameFieldDao() {
        dbConnection = new DBConnection();
    }
    
    public GameDie getGameDieOfField(PatternCardField patternCardField, Player player, Game game) {
        GameDie die = null;
        
        try {
            ResultSet rs = dbConnection.executeQuery(new Query(
                        "SELECT gamedie.dienumber, gamedie.diecolor, gamedie.eyes FROM playerframefield "
                        + "JOIN gamedie ON gamedie.idgame = playerframefield.idgame "
                        + "JOIN die ON gamedie.dienumber = die.number AND gamedie.diecolor = die.color"
                        + "WHERE player_idplayer=? AND position_x=? AND position_y=? AND idgame=?", "query"
                    ),
                        new QueryParameter(QueryParameter.INT, player.getId()),
                        new QueryParameter(QueryParameter.INT, patternCardField.getxPos()),
                        new QueryParameter(QueryParameter.INT, patternCardField.getyPos()),
                        new QueryParameter(QueryParameter.INT, game.getId())
                    );
            if (rs.next()) {
                die = new GameDie(
                        rs.getInt("gamedie.dienumber"), 
                        rs.getString("gamedie.diecolor"), 
                        rs.getInt("eyes"));
                die.setPatternCardField(patternCardField);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        
        return die;
    }

}
