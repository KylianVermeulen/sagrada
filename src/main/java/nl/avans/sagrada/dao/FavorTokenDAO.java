package nl.avans.sagrada.dao;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.FavorToken;
import nl.avans.sagrada.model.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FavorTokenDAO {
    private DBConnection dbConnection;

    public ArrayList<FavorToken> getFavortokensOfPlayer(Player player) {
        dbConnection = new DBConnection();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM gamefavortoken WHERE idplayer=?", "query",
                            new QueryParameter(QueryParameter.INT, player.getId()))
            );
            ArrayList<FavorToken> list = new ArrayList<FavorToken>();
            while (rs.next()) {
                FavorToken favorToken = new FavorToken(rs.getInt("idfavortoken"), rs.getInt("idgame"),
                        rs.getInt("idplayer"), rs.getInt("gametoolcard"));
                list.add(favorToken);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
