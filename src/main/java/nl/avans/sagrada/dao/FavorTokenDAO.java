package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.FavorToken;
import nl.avans.sagrada.model.Player;

public class FavorTokenDAO {
    private DBConnection dbConnection;

    /**
     * Constructor, Initializes DBConnection
     */
    public FavorTokenDAO() {
        dbConnection = new DBConnection();
    }

    /**
     * This method will return a list of all favor tokens of a player given as parameter.
     *
     * @param player The player to retrieve all favor tokens.
     * @return A list of favor tokens.
     */
    public ArrayList<FavorToken> getFavortokensOfPlayer(Player player) {
        ArrayList<FavorToken> list = new ArrayList<>();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM gamefavortoken WHERE idplayer=?", "query",
                            new QueryParameter(QueryParameter.INT, player.getId()))
            );
            while (rs.next()) {
                FavorToken favorToken = new FavorToken(rs.getInt("idfavortoken"), player);
                list.add(favorToken);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}