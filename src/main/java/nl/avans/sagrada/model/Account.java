package nl.avans.sagrada.model;

import nl.avans.sagrada.dao.PlayerDAO;

import java.util.ArrayList;

public class Account {
    private String username;
    private String password;
    private ArrayList<Player> players;

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

    public void setPlayers() {
        PlayerDAO playerDAO = new PlayerDAO();
        this.players = playerDAO.getPlayersOfAccount(this);
    }
}
