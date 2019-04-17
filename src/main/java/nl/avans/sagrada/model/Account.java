package nl.avans.sagrada.model;

import nl.avans.sagrada.dao.AccountDAO;
import nl.avans.sagrada.dao.InviteDAO;
import nl.avans.sagrada.dao.PlayerDAO;

import java.util.ArrayList;

public class Account {
    private String username;
    private String password;
    private ArrayList<Player> players;
    private ArrayList<Invite> pendingInvites;


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
        PlayerDAO playerDAO = new PlayerDAO();
        players = playerDAO.getPlayersOfAccount(this);
        return players;
    }
    
    /**
     * Added method to get all the games of a account
     * @return ArrayList<Game>
     */
    public ArrayList<Game> getGames() {
        if (players.size() == 0) {
            getPlayers(); 
        }
        
        ArrayList<Game> games = new ArrayList<>();
        for (Player player : players) {
            games.add(player.getGame());
        }
        return games;
    }
}
