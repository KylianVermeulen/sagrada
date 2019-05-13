package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.paint.Color;
import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.FavorToken;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Player;
import nl.avans.sagrada.model.ToolCard;

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
            ResultSet rs = dbConnection.executeQuery(new Query(
                    "SELECT * FROM gamefavortoken WHERE idplayer=? AND gametoolcard IS NULL",
                    "query", new QueryParameter(QueryParameter.INT, player.getId())));
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
     * Returns the next available idfavortoken in the database table, which allows for a new entry
     * to be added to this table.
     *
     * @return The next favortoken id that is available
     */
    public int getNextFavorTokenId() {
        int favorTokenId = 0;
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT MAX(idfavortoken) AS highestFavorTokenId FROM gamefavortoken",
                            "query"));
            if (rs.next()) {
                favorTokenId = rs.getInt("highestFavorTokenId") + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favorTokenId;
    }

    /**
     * Add a favor token to the database.
     *
     * @param favorToken The favor token to add.
     */
    public void addFavorToken(FavorToken favorToken) {
        try {
            ResultSet rs = dbConnection.executeQuery(new Query(
                            "INSERT INTO gamefavortoken (idfavortoken, idgame, idplayer) VALUES (?, ?, ?)",
                            "update"), new QueryParameter(QueryParameter.INT, favorToken.getId()),
                    new QueryParameter(QueryParameter.INT, favorToken.getGame().getId()),
                    new QueryParameter(QueryParameter.INT, favorToken.getPlayer().getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets a favor token for a player, but does not bind a favor token to a tool card.
     *
     * @param favorToken FavorToken
     * @param game Game
     * @param player Player
     */
    public void setFavortokenForPlayer(FavorToken favorToken, Game game, Player player) {
        try {
            ResultSet rs = dbConnection.executeQuery(new Query(
                            "UPDATE gamefavortoken SET idfavortoken=?, idgame=?, idplayer=? WHERE idfavortoken=?",
                            "update"), new QueryParameter(QueryParameter.INT, favorToken.getId()),
                    new QueryParameter(QueryParameter.INT, game.getId()),
                    new QueryParameter(QueryParameter.INT, player.getId()),
                    new QueryParameter(QueryParameter.INT, favorToken.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the game favor token table in the database to link a favor token to a tool card.
     *
     * @param favorToken FavorToken
     * @param toolCard Toolcard
     * @param game Game
     */
    public void setFavortokensForToolCard(FavorToken favorToken, ToolCard toolCard, Game game) {
        ToolCardDao toolCardDao = new ToolCardDao();
        try {
            ResultSet rs = dbConnection.executeQuery(new Query(
                            "UPDATE gamefavortoken SET idfavortoken=?, idgame=?, idplayer=?, gametoolcard=? WHERE idfavortoken=?",
                            "update"), new QueryParameter(QueryParameter.INT, favorToken.getId()),
                    new QueryParameter(QueryParameter.INT, game.getId()),
                    new QueryParameter(QueryParameter.INT, favorToken.getPlayer().getId()),
                    new QueryParameter(QueryParameter.INT,
                            toolCardDao.getGameToolCardForToolCardId(toolCard.getId(),
                                    game.getId())),
                    new QueryParameter(QueryParameter.INT, favorToken.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Method to get all the paid favortokens from a toolcard
     * @param toolcard ToolCard
     * @param game Game
     * @return
     */
    public ArrayList<FavorToken> getToolCardTokens(ToolCard toolcard, Game game) {
        ArrayList<FavorToken> tokens = new ArrayList<>();
        ToolCardDao toolcarddao = new ToolCardDao();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM gamefavortoken WHERE idgame=? AND gametoolcard=?", "query"), 
                    new QueryParameter(QueryParameter.INT, game.getId()),
                    new QueryParameter(QueryParameter.INT, toolcarddao.getGameToolCardForToolCardId(toolcard.getId(), game.getId()))
            );
            while(rs.next()) {
                FavorToken token = new FavorToken();
                token.setId(rs.getInt("idfavortoken"));
                token.setPlayer(new PlayerDao().getPlayerById(rs.getInt("idplayer")));
                token.setGame(new GameDao().getGameById(rs.getInt("idgame")));
                tokens.add(token);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tokens;
    }
}
