package nl.avans.sagrada.model;

import java.util.ArrayList;

import nl.avans.sagrada.dao.AccountDAO;
import nl.avans.sagrada.dao.InviteDAO;

public class Account {
    private String username;
    private String password;
    private ArrayList<Player> players;
    private ArrayList<Invite> pendingInvites;
    
    public Account() {}
    
    /**
     * Gets a user from the database by the username 
     * and fills them in the current Account object
     * @param username
     */
    public Account(String username) {
        AccountDAO accountDao = new AccountDAO();
        Account account = accountDao.getAccountByUsername(username);
        this.username = account.getUsername();
        password = account.getPassword();
    }
    
    /**
     * Fills in the username and password from the object
     * @param username
     * @param password
     */
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    /**
     * Get all the pending invites from the user
     * @return ArrayList with all invites
     */
    public ArrayList<Invite> getAllPendingInvites() {
        InviteDAO inviteDao = new InviteDAO();
        pendingInvites = inviteDao.getAllPendingInvitesOfAccount(this);
        return pendingInvites;
    }
    
    /**
     * Get the username from the object
     * @return String with the username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Gets the password from the object
     * @return String with the password
     */
    public String getPassword() {
        return password;
    }
}
