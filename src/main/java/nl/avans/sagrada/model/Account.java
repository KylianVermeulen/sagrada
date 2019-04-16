package nl.avans.sagrada.model;

import nl.avans.sagrada.dao.AccountDAO;
import nl.avans.sagrada.dao.PlayerDAO;

import java.util.ArrayList;

import nl.avans.sagrada.dao.AccountDAO;
import nl.avans.sagrada.dao.InviteDAO;

public class Account {
    private String username;
    private String password;
    private ArrayList<Player> players;
    private ArrayList<Invite> pendingInvites;
    
    
    /**
     * Fills in the username and password from the object
     * @param username
     * @param password
     */
    public Account(String username) {
        AccountDAO accountDao = new AccountDAO();
        Account account = accountDao.getAccountByUsername(username);
        this.username = account.getUsername();
        password = account.getPassword();
    }
    


    /**
     * Empty constructor
     */
    public Account() {
    }

    /**
     * Full constructor
     *
     * @param username String
     * @param password String
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
     * Add object to database
     */
    public void add() {
        AccountDAO accountDAO = new AccountDAO();
        accountDAO.addAccount(this);
    }

    /**
     * Update object in database
     */
    public void save() {
        AccountDAO accountDAO = new AccountDAO();
        accountDAO.updateAccount(this);
    }

    /**
     * Get username from Account
     *
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set username to Player
     *
     * @param username String
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get password from Player
     *
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password to player
     *
     * @param password String
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get Players from Account
     *
     * @return ArrayList<Player>
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Set Players to Account
     *
     * @param players ArrayList<Player>
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * Get Players from database using Account and set to Account
     */
    public void setPlayers() {
        PlayerDAO playerDAO = new PlayerDAO();
        this.players = playerDAO.getPlayersOfAccount(this);
    }
}
