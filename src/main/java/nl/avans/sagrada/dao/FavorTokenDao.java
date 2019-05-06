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
     * Returns the next available idfavortoken in the database table, which allows for a new
     * entry to be added to this table.
     * 
     * @return The next favortoken id that is available
     */
    public int getNextFavorTokenId() {
        int favorTokenId = 0;
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT MAX(idfavortoken) AS highestFavorTokenId FROM gamefavortoken", "query")
            );
            if (rs.next()) {
                favorTokenId = rs.getInt("highestFavorTokenId") + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favorTokenId;
    }
    
    /**
     * Sets a favortoken for a specific game.
     * 
     * @param game Game
     */
    public void setFavortokenForGame(Game game) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("INSERT INTO gamefavortoken (idfavortoken, idgame) VALUES (?, ?)", "update"),
                    new QueryParameter(QueryParameter.INT, getNextFavorTokenId()),
                    new QueryParameter(QueryParameter.INT, game.getId())
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns a favortoken from the database, specified by the id given as parameter.
     * 
     * @param id int
     * @return The favortoken belonging to the id, which is given as parameter
     */
    public FavorToken getFavorTokenById(int id) {
        ToolcardDao toolcardDao = new ToolcardDao();
        PlayerDao playerDao = new PlayerDao();
        GameDao gameDao = new GameDao();
        FavorToken favorToken = new FavorToken();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM gamefavortoken WHERE idfavortoken=?", "query"),
                    new QueryParameter(QueryParameter.INT, id)
            );
            if (rs.next()) {
                favorToken.setId(rs.getInt("idfavortoken"));;
                favorToken.setGame(gameDao.getGameById(rs.getInt("idgame")));
                favorToken.setPlayer(playerDao.getPlayerById(rs.getInt("idplayer")));
                favorToken.setToolcard(toolcardDao.getToolcardById(rs.getInt("gametoolcard")));
            }
        } catch (Exception e) {
            favorToken = null;
            e.printStackTrace();
        }
        return favorToken;
    }
    
    public void setFavortokenForPlayer(Game game, Player player) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("UPDATE gamefavortoken (idfavortoken, idgame, idplayer) VALUES (?, ?, ?)", "update"),
                    new QueryParameter(QueryParameter.INT, getNextFavorTokenId()),
                    new QueryParameter(QueryParameter.INT, game.getId()),
                    new QueryParameter(QueryParameter.INT, player.getId())
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Updates the gamefavortoken table in the database to link a favortoken to a toolcard.
     * 
     * @param favorToken FavorToken
     * @param toolcard Toolcard
     * @param game Game
     */
    public void setFavortokensForToolcard(FavorToken favorToken, Toolcard toolcard, Game game) {
        ToolcardDao toolcardDao = new ToolcardDao();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "UPDATE gamefavortoken SET idfavortoken=?, idgame=?, idplayer=?, gametoolcard=? WHERE idfavortoken=?",
                            "update"),
                    new QueryParameter(QueryParameter.INT, favorToken.getId()),
                    new QueryParameter(QueryParameter.INT, favorToken.getGame()),
                    new QueryParameter(QueryParameter.INT, favorToken.getPlayer()),
                    new QueryParameter(QueryParameter.INT, toolcardDao.getGameToolcardForToolcardId(toolcard.getId(), game.getId()))
            );                    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}