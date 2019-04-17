package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.model.Invite;
import nl.avans.sagrada.model.Player;

public class InviteDAO {
    private DBConnection dbConnection;
    
    public InviteDAO() {
        dbConnection = new DBConnection();
    }

    /**
     * get all the invites from a account
     * @param account
     * @return ArrayList containing all the invites
     */
    public ArrayList<Invite> getInvitesOfAccount(Account account) {
        ArrayList<Invite> invites = new ArrayList<>();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM player WHERE username=?", "query",
                    new QueryParameter(QueryParameter.STRING, account.getUsername()))
            );
            while (rs.next()) {
                PlayerDAO playerDao = new PlayerDAO();
                Invite invite = new Invite();
                Player player = playerDao.getPlayerById(rs.getInt("idplayer"));
                invite.setSendedPlayer(player);
                invite.setInvitedAccount(account);
                String inviteStatus = rs.getString("playstatus_playstatus");
                if (inviteStatus.equals("accepted")) {
                    invite.acceptInvite();
                }
                else if (inviteStatus.equals("refused")) {
                    invite.denyInvite();
                }
                // When they do not have accepted or rejected, this is not important for a invite
                // So we ignore that
                invites.add(invite);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return invites;
    }
    
    /**
     * Get all invites that are pending
     * So the invites that are still waiting for a response
     * @param account
     * @return ArrayList with all the invites
     */
    public ArrayList<Invite> getAllPendingInvitesOfAccount(Account account) {
        ArrayList<Invite> inviteList = getInvitesOfAccount(account);
        ArrayList<Invite> pendingInvites = new ArrayList<>();
        for(Invite invite: inviteList) {
            if (invite.isPending()) {
                pendingInvites.add(invite);
            }
        }
        return pendingInvites;
        
    }

    public void addInvite(Invite invite) {

    }
}
