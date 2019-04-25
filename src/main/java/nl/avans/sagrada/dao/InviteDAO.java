package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Invite;
import nl.avans.sagrada.model.Player;

public class InviteDAO {
    private DBConnection dbConnection;

    public InviteDAO() {
        dbConnection = new DBConnection();
    }

    public ArrayList<Invite> getInvitesOfAccount(Account account) {
        ArrayList<Invite> invites = new ArrayList<>();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM player WHERE username=?", "query",
                            new QueryParameter(QueryParameter.STRING, account.getUsername()))
            );
            while (rs.next()) {
                GameDAO gameDao = new GameDAO();
                PlayerDAO playerDao = new PlayerDAO();
                Player player = playerDao.getPlayerById(rs.getInt("idplayer"));
                Game game = gameDao.getGameById(rs.getInt("game_idgame"));
                Invite invite = new Invite();
                invite.setInvitedAccount(account);
                invite.setPlayer(player);
                invite.setGame(game);
                String inviteStatus = rs.getString("playstatus_playstatus");
                if (inviteStatus.equals("accepted")) {
                    invite.acceptInvite();
                } else if (inviteStatus.equals("refused")) {
                    invite.denyInvite();
                }
                // When they do not have accepted or rejected, this is not important for a invite
                // So we ignore that
                invites.add(invite);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invites;
    }

    public ArrayList<Invite> getAllPendingInvitesOfAccount(Account account) {
        ArrayList<Invite> inviteList = getInvitesOfAccount(account);
        ArrayList<Invite> pendingInvites = new ArrayList<>();
        for (Invite invite : inviteList) {
            Player player = invite.getPlayer();
            if (player.getPlayerStatus().equals("challengee")) {
                pendingInvites.add(invite);
            }
        }
        return pendingInvites;
    }

    public void addInvite(Invite invite) {
        try {
            PlayerDAO playerDao = new PlayerDAO();
            int nextPlayerId = playerDao.getNextPlayerId();
            String username = invite.getInvitedAccount().getUsername();
            Game game = invite.getGame();
            String privateObjectiveColor = game.getRandomAvailablePrivateColor();
            int seqNr = this.getSeqNrForNextPlayer(game);
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "INSERT INTO player (idplayer, username, game_idgame, playstatus_playstatus, seqnr, isCurrentPlayer, private_objectivecard_color, patterncard_idpatterncard, score) VALUES (?, ?, ?, ?, ?, '0', ?, NULL, NULL);",
                            "update"),
                    new QueryParameter(QueryParameter.INT, nextPlayerId),
                    new QueryParameter(QueryParameter.STRING, username),
                    new QueryParameter(QueryParameter.INT, game.getId()),
                    new QueryParameter(QueryParameter.STRING, "challengee"),
                    new QueryParameter(QueryParameter.INT, seqNr),
                    new QueryParameter(QueryParameter.STRING, privateObjectiveColor)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getSeqNrForNextPlayer(Game game) {
        int nextSeqnr = 1;
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT MAX(seqnr) AS highestSeqnr FROM player WHERE game_idgame=?",
                            "query"),
                    new QueryParameter(QueryParameter.INT, game.getId())
            );
            if (rs.next()) {
                nextSeqnr = rs.getInt("highestSeqnr") + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nextSeqnr;
    }

    public void updateInvite(Invite invite) {
        int playerId = invite.getPlayer().getId();

        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("UPDATE player SET playstatus_playstatus=?  WHERE idplayer=?",
                            "update"),
                    new QueryParameter(QueryParameter.STRING, invite.getStatus()),
                    new QueryParameter(QueryParameter.INT, playerId)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
