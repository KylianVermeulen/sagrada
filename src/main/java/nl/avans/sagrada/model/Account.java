package nl.avans.sagrada.model;

import nl.avans.sagrada.dao.AccountDAO;
import nl.avans.sagrada.dao.PlayerDAO;

import java.util.ArrayList;

public class Account {
    private String username;
    private String password;
    private ArrayList<Player> players;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

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
