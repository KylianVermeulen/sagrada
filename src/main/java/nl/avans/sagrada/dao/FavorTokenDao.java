package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.FavorToken;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.model.Toolcard;

public class FavorTokenDao {
    private DBConnection dbConnection;

    /**
     * Constructor, Initializes DBConnection
     */
    public FavorTokenDao() {
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

    /**
     * Add a favor token to the database.
     *
     * @param favorToken The favor token to add.
     */
    public void addFavorToken(FavorToken favorToken) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "INSERT INTO gamefavortoken (idfavortoken, idgame, idplayer) VALUES (?, ?, ?)",
                            "update"),
                    new QueryParameter(QueryParameter.INT, favorToken.getId()),
                    new QueryParameter(QueryParameter.INT, favorToken.getGame().getId()),
                    new QueryParameter(QueryParameter.INT, favorToken.getPlayer().getId())
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the next available favor token id.
     *
     * @return favorTokenId.
     */
    public int getNextFavorTokenId() {
        int favorTokenId = 0;
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT MAX(idfavortoken) AS highestFavorTokenId FROM gamefavortoken",
                            "query")
            );
            if (rs.next()) {
                favorTokenId = rs.getInt("highestFavorTokenId") + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return favorTokenId;
    }
}
