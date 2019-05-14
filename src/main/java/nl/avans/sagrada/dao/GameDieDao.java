package nl.avans.sagrada.dao;

import nl.avans.sagrada.database.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.GameDie;

public class GameDieDao {
    private DBConnection dbConnection;

    /**
     * Constructor, Initializes DBConnection
     */
    public GameDieDao() {
        dbConnection = new DBConnection();
    }

    public void placeDie(GameDie die, PatternCardField patterncardfield, Player player) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("UPDATE playerframefield SET dienumber=?, diecolor=? WHERE player_idplayer=? AND position_y=? AND position_x=? AND idgame=? ", "update"),
                    new QueryParameter(QueryParameter.INT, die.getNumber()),
                    new QueryParameter(QueryParameter.STRING, die.getColor()),
                    new QueryParameter(QueryParameter.INT, player.getGame().getId()),
                    new QueryParameter(QueryParameter.INT, patterncardfield.getyPos()),
                    new QueryParameter(QueryParameter.INT, patterncardfield.getxPos()),
                    new QueryParameter(QueryParameter.INT, player.getGame().getId())
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
