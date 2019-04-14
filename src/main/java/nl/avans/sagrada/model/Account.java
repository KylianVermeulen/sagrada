package nl.avans.sagrada.model;

import nl.avans.sagrada.dao.PlayerDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Account {
    private String username;
    private String password;
    private ArrayList<Player> players;

    /**
     * Constructor
     *
     * @param rs ResultSet from DAO
     */
    public Account(ResultSet rs) throws SQLException {
        setUsername(rs.getString("username"));
        setPassword(rs.getString("password"));
        setPlayers(new PlayerDAO().getPlayersOfAccount(this));
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

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
}
