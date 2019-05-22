package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;

public class GameDieDao {
    private DBConnection dbConnection;

    /**
     * Constructor, Initializes DBConnection
     */
    public GameDieDao() {
        dbConnection = new DBConnection();
    }

    /**
     * Adds gamedie to game in the database
     *
     * @param game Game
     * @param gameDie GameDie
     */
    public void addDie(Game game, GameDie gameDie) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "INSERT INTO gamedie (idgame, dienumber, diecolor, eyes) VALUES (?, ?, ?, ?)",
                            "update"),
                    new QueryParameter(QueryParameter.INT, game.getId()),
                    new QueryParameter(QueryParameter.INT, gameDie.getNumber()),
                    new QueryParameter(QueryParameter.STRING, gameDie.getColor()),
                    new QueryParameter(QueryParameter.INT, gameDie.getEyes())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a gameDie and it's attributes to the database.
     *
     * @param game Game
     * @param gameDie GameDie
     */
    public void addDie(Game game, GameDie gameDie, int round) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "INSERT INTO gamedie (idgame, dienumber, diecolor, eyes, round) VALUES (?, ?, ?, ?, ?)",
                            "update"),
                    new QueryParameter(QueryParameter.INT, game.getId()),
                    new QueryParameter(QueryParameter.INT, gameDie.getNumber()),
                    new QueryParameter(QueryParameter.STRING, gameDie.getColor()),
                    new QueryParameter(QueryParameter.INT, gameDie.getEyes()),
                    new QueryParameter(QueryParameter.INT, round)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the die
     *
     * @param game Game
     * @param gameDie GameDie
     * @param round int
     */
    public void updateDie(Game game, GameDie gameDie) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "UPDATE gamedie SET round=?, roundtrack=?, inFirstTurn=? WHERE idgame=? AND dienumber=? AND diecolor=?",
                            "update"),
                    new QueryParameter(QueryParameter.INT, gameDie.getRound()),
                    new QueryParameter(QueryParameter.INT, gameDie.isOnRoundTrack() ? gameDie.getRound() : null),
                    new QueryParameter(QueryParameter.BOOLEAN, gameDie.isInFirstTurn()),
                    new QueryParameter(QueryParameter.INT, game.getId()),
                    new QueryParameter(QueryParameter.INT, gameDie.getNumber()),
                    new QueryParameter(QueryParameter.STRING, gameDie.getColor())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets all the dice from a game.
     *
     * @param game Game
     * @return ArrayList<GameDie>
     */
    public ArrayList<GameDie> getDiceFromGame(Game game) {
        ArrayList<GameDie> gameDice = new ArrayList<GameDie>();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM gamedie WHERE idgame=?", "query"),
                    new QueryParameter(QueryParameter.INT, game.getId())
            );
            while (rs.next()) {
                GameDie gameDie = new GameDie(
                        rs.getInt("number"),
                        rs.getString("color"),
                        rs.getInt("eyes")
                );
                gameDie.setInFirstTurn(rs.getBoolean("inFirstTurn"));
                gameDice.add(gameDie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameDice;
    }

    /**
     * Gets the die from a game
     *
     * @param game Game
     * @param die GameDie
     * @return GameDie
     */
    public GameDie getDie(Game game, GameDie die) {
        GameDie gameDie = null;
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM gamedie WHERE idgame=? AND dienumber=? AND diecolor=?",
                            "query"),
                    new QueryParameter(QueryParameter.INT, game.getId()),
                    new QueryParameter(QueryParameter.INT, die.getNumber()),
                    new QueryParameter(QueryParameter.STRING, die.getColor())
            );
            while (rs.next()) {
                gameDie = new GameDie(
                        rs.getInt("dienumber"),
                        rs.getString("diecolor"),
                        rs.getInt("eyes"),
                        rs.getInt("round")
                );
                gameDie.setInFirstTurn(rs.getBoolean("inFirstTurn"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameDie;
    }

    /**
     * Gets the dice for a round from a game
     * @param game Game
     * @return ArrayList<GameDie>
     */
    public ArrayList<GameDie> getRoundDice(Game game) {
        ArrayList<GameDie> gameDice = new ArrayList<GameDie>();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM gamedie WHERE idgame=? AND round=?", "query"),
                    new QueryParameter(QueryParameter.INT, game.getId()),
                    new QueryParameter(QueryParameter.INT, game.getRound())
            );
            while (rs.next()) {
                GameDie gameDie = new GameDie(
                        rs.getInt("dienumber"),
                        rs.getString("diecolor"),
                        rs.getInt("eyes")
                );
                gameDie.setIsOnOfferTable(true);
                gameDie.setRound(rs.getInt("round"));
                gameDie.setInFirstTurn(rs.getBoolean("inFirstTurn"));
                gameDice.add(gameDie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameDice;
    }
    
    /**
     * Gets all the available dice of a round
     * @param game
     * @return ArrayList<GameDie>
     */
    public ArrayList<GameDie> getAvailableDiceOfRound(Game game) {
        ArrayList<GameDie> gameDice = new ArrayList<GameDie>();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT gamedie.* \n" + 
                            "FROM gamedie \n" + 
                            "LEFT JOIN \n" + 
                            "playerframefield ON gamedie.dienumber = playerframefield.dienumber \n" + 
                            "AND gamedie.idgame = playerframefield.idgame \n" + 
                            "AND gamedie.diecolor = playerframefield.diecolor \n" + 
                            "WHERE \n" + 
                            "(playerframefield.dienumber IS NULL AND playerframefield.diecolor IS NULL AND playerframefield.idgame IS NULL) \n" + 
                            "AND gamedie.idgame=? AND round=?", "query"),
                    new QueryParameter(QueryParameter.INT, game.getId()),
                    new QueryParameter(QueryParameter.INT, game.getRound())
            );
            while (rs.next()) {
                GameDie gameDie = new GameDie(
                        rs.getInt("dienumber"),
                        rs.getString("diecolor"),
                        rs.getInt("eyes")
                );
                gameDie.setInFirstTurn(rs.getBoolean("inFirstTurn"));
                gameDie.setRound(rs.getInt("round"));
                gameDie.setIsOnOfferTable(true);
                gameDice.add(gameDie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameDice;
    }
    
    /**
     * Gets the dice from the round track in game.
     *
     * @param game The game.
     * @return ArrayList<GameDie>
     */
    public ArrayList<GameDie> getDiceOnRoundTrackFromGame(Game game) {
        ArrayList<GameDie> gameDice = new ArrayList<>();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM gamedie WHERE idgame=? AND roundtrack IS NOT NULL AND round < ?", "query"),
                    new QueryParameter(QueryParameter.INT, game.getId()),
                    new QueryParameter(QueryParameter.INT, game.getRound())
            );
            while (rs.next()) {
                GameDie gameDie = new GameDie(
                        rs.getInt("dienumber"),
                        rs.getString("diecolor"),
                        rs.getInt("eyes")
                );
                gameDie.setOnRoundTrack(rs.getBoolean("roundtrack"));
                gameDie.setIsOnOfferTable(false);
                gameDie.setRound(rs.getInt("round"));
                gameDice.add(gameDie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gameDice;
    }

    /**
     * Places a die on the patterncardfield in the db
     * 
     * @param die
     * @param patterncardfield
     * @param player
     */
    public void placeDie(GameDie die, PatternCardField patterncardfield, Player player) {
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
     * Updates the amount of eyes for a certain die.
     * 
     * @param game Game
     * @param gameDie GameDie
     */
    public void updateDieEyes(Game game, GameDie gameDie) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "UPDATE gamedie SET eyes=? WHERE idgame=? AND round=? AND dienumber=?",
                            "update"),
                    new QueryParameter(QueryParameter.INT, gameDie.getEyes()),
                    new QueryParameter(QueryParameter.INT, game.getId()),
                    new QueryParameter(QueryParameter.INT, game.getRound()),
                    new QueryParameter(QueryParameter.INT, gameDie.getNumber())
            );   
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
