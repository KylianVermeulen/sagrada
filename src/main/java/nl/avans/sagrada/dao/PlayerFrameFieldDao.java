package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    /**
     * Gets the gameDie of a field
     *
     * @return GameDie
     */
    public GameDie getGameDieOfField(PatternCardField patternCardField, Player player) {
        GameDie die = null;
        try {
            Game game = player.getGame();
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "SELECT playerframefield.idgame, player_idplayer, position_x, position_y, playerframefield.dienumber, playerframefield.diecolor, g.eyes FROM playerframefield INNER JOIN gamedie g on playerframefield.idgame = g.idgame and playerframefield.dienumber = g.dienumber and playerframefield.diecolor = g.diecolor WHERE player_idplayer=? AND position_x=? AND position_y=? AND playerframefield.idgame=?",
                            "query"),
                    new QueryParameter(QueryParameter.INT, player.getId()),
                    new QueryParameter(QueryParameter.INT, patternCardField.getxPos()),
                    new QueryParameter(QueryParameter.INT, patternCardField.getyPos()),
                    new QueryParameter(QueryParameter.INT, game.getId())
            );
            if (rs.next()) {
                die = new GameDie(
                        rs.getInt("dienumber"),
                        rs.getString("diecolor"),
                        rs.getInt("eyes")
                );
                die.setIsOnOfferTable(false);
                patternCardField.setDie(die);
            }
            rs.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return die;
    }

    /**
     * Updates the location of one die on the framefield
     */
    public void updateDieLocation(GameDie die, PatternCardField patterncardfield, Player player) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "UPDATE playerframefield SET dienumber=?, diecolor=? WHERE player_idplayer=? AND position_y=? AND position_x=? AND idgame=?",
                            "update"),
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

    /**
     * Adds a die to a field
     */
    public void addDieToField(GameDie die, PatternCardField patternCardField, Player player) {
        try {
            ResultSet rs = dbConnection.executeQuery(new Query(
                    "INSERT INTO playerframefield (player_idplayer, position_x, position_y, idgame, dienumber, diecolor) VALUES (?, ?, ?, ?, ?, ?)",
                    "update",
                    new QueryParameter(QueryParameter.INT, player.getId()),
                    new QueryParameter(QueryParameter.INT, patternCardField.getxPos()),
                    new QueryParameter(QueryParameter.INT, patternCardField.getyPos()),
                    new QueryParameter(QueryParameter.INT, player.getGame().getId()),
                    new QueryParameter(QueryParameter.INT, die.getNumber()),
                    new QueryParameter(QueryParameter.STRING, die.getColor())
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        die.setIsOnOfferTable(false);
    }

    /**
     * Removes a die from the field that it is of a player
     */
    public void removeDie(GameDie die, PatternCardField patternCardField, Player player) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "DELETE FROM playerframefield WHERE player_idplayer=? AND position_y=? AND position_x=? AND idgame=?",
                            "update"),
                    new QueryParameter(QueryParameter.INT, player.getId()),
                    new QueryParameter(QueryParameter.INT, patternCardField.getyPos()),
                    new QueryParameter(QueryParameter.INT, patternCardField.getxPos()),
                    new QueryParameter(QueryParameter.INT, player.getGame().getId())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
