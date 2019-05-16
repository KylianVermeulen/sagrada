package nl.avans.sagrada.dao;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlayerDao {
    private DBConnection dbConnection;

    /**
     * Constructor, Initializes DBConnection
     */
    public PlayerDao() {
        dbConnection = new DBConnection();
    }

    public ArrayList<Player> getPlayersOfAccount(Account account) {
        ArrayList<Player> list = new ArrayList<>();
        try {
            ResultSet rs = dbConnection
                    .executeQuery(new Query("SELECT * FROM player WHERE username=?", "query",
                            new QueryParameter(QueryParameter.STRING, account.getUsername())));
            while (rs.next()) {
                Player player = new Player();
                Game game = new GameDao().getGameById(rs.getInt("game_idgame"));
                player.setId(rs.getInt("idplayer"));
                player.setAccount(account);
                player.setGame(game);
                player.setPlayerStatus(rs.getString("playstatus_playstatus"));
                player.setSeqnr(rs.getInt("seqnr"));
                player.setIsCurrentPlayer(rs.getBoolean("isCurrentPlayer"));
                player.setPrivateObjectivecardColor(rs.getString("private_objectivecard_color"));
                player.setScore(rs.getInt("score"));
                list.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updatePlayer(Player player) {
        try {
            ResultSet rs = dbConnection.executeQuery(new Query(
                            "UPDATE player SET username=?, game_idgame=?, playstatus_playstatus=?, seqnr=?, isCurrentPlayer=?, private_objectivecard_color=?, score=? WHERE idplayer=?",
                            "update"),
                    new QueryParameter(QueryParameter.STRING, player.getAccount().getUsername()),
                    new QueryParameter(QueryParameter.INT, player.getGame().getId()),
                    new QueryParameter(QueryParameter.STRING, player.getPlayerStatus()),
                    new QueryParameter(QueryParameter.INT, player.getSeqnr()),
                    new QueryParameter(QueryParameter.BOOLEAN, player.isCurrentPlayer()),
                    new QueryParameter(QueryParameter.STRING,
                            player.getPrivateObjectivecardColor()),
                    new QueryParameter(QueryParameter.INT, player.getScore()),
                    new QueryParameter(QueryParameter.INT, player.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPlayer(Player player) {
        try {
            ResultSet rs = dbConnection.executeQuery(new Query(
                            "INSERT INTO player (idplayer, username, game_idgame, playstatus_playstatus, seqnr, isCurrentPlayer, private_objectivecard_color, score) VALUES (?,?, ?, ?, ?, ?, ?, ?)",
                            "update"), new QueryParameter(QueryParameter.INT, player.getId()),
                    new QueryParameter(QueryParameter.STRING, player.getAccount().getUsername()),
                    new QueryParameter(QueryParameter.INT, player.getGame().getId()),
                    new QueryParameter(QueryParameter.STRING, player.getPlayerStatus()),
                    new QueryParameter(QueryParameter.INT, player.getSeqnr()),
                    new QueryParameter(QueryParameter.BOOLEAN, player.isCurrentPlayer()),
                    new QueryParameter(QueryParameter.STRING,
                            player.getPrivateObjectivecardColor()),
                    new QueryParameter(QueryParameter.INT, player.getScore()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Player getPlayerById(int id) {
        Player player = new Player();
        try {
            ResultSet rs =
                    dbConnection.executeQuery(new Query("SELECT * FROM player WHERE idplayer=?",
                            "query", new QueryParameter(QueryParameter.INT, id)));
            if (rs.next()) {
                AccountDao accountDao = new AccountDao();
                Account account = accountDao.getAccountByUsername(rs.getString("username"));
                player.setId(rs.getInt("idplayer"));
                player.setPlayerStatus(rs.getString("playstatus_playstatus"));
                player.setPrivateObjectivecardColor(rs.getString("private_objectivecard_color"));
                player.setSeqnr(rs.getInt("seqnr"));
                player.setIsCurrentPlayer(rs.getBoolean("isCurrentPlayer"));
                player.setAccount(account);
            }
        } catch (Exception e) {
            player = null;
            e.printStackTrace();
        }
        return player;
    }

    public int getNextPlayerId() {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT MAX(idplayer) AS highestId FROM player", "query"));
            if (rs.next()) {
                int nextId = rs.getInt("highestId") + 1;
                return nextId;
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return 0;
    }

    public Player getPlayerByAccountAndGame(Account account, Game game) {
        Player player = new Player();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT idplayer FROM player WHERE username=? AND game_idgame=?",
                            "query"),
                    new QueryParameter(QueryParameter.STRING, account.getUsername()),
                    new QueryParameter(QueryParameter.INT, game.getId()));
            if (rs.next()) {
                int playerId = rs.getInt("idplayer");
                player = getPlayerById(playerId);
            }
        } catch (Exception e) {
            player = null;
            e.printStackTrace();
        }
        return player;
    }

    /**
     * Update the selected patterncard of a player.
     *
     * @param player The player.
     * @param patternCard The patterncard.
     */
    public void updateSelectedPatternCard(Player player, PatternCard patternCard) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("UPDATE player SET patterncard_idpatterncard=? WHERE idplayer=?",
                            "update"),
                    new QueryParameter(QueryParameter.INT, patternCard.getId()),
                    new QueryParameter(QueryParameter.INT, player.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
