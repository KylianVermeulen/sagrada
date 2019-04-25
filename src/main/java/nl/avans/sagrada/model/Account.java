package nl.avans.sagrada.model;

import java.util.ArrayList;
import nl.avans.sagrada.dao.InviteDAO;
import nl.avans.sagrada.dao.PlayerDAO;

public class Account {
    private String username;
    private String password;
    private ArrayList<Player> players;
    private ArrayList<Invite> pendingInvites;

    public Account() {
        players = new ArrayList<Player>();
        pendingInvites = new ArrayList<Invite>();
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        players = new ArrayList<Player>();
        pendingInvites = new ArrayList<Invite>();
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

    public ArrayList<Invite> getPendingInvites() {
        return pendingInvites;
    }

    public void setPendingInvites(ArrayList<Invite> pendingInvites) {
        this.pendingInvites = pendingInvites;
    }

    public ArrayList<Player> getPlayers() {
        PlayerDAO playerDAO = new PlayerDAO();
        players = playerDAO.getPlayersOfAccount(this);
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

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

    public ArrayList<Game> getActiveGames() {
        ArrayList<Game> games = getGames();
        ArrayList<Game> activeGames = new ArrayList<>();

        for (Game game : games) {
            if (game.isActive()) {
                activeGames.add(game);
            }
        }
        return activeGames;
    }

    public ArrayList<Invite> getAllPendingInvites() {
        InviteDAO inviteDAO = new InviteDAO();
        pendingInvites = inviteDAO.getAllPendingInvitesOfAccount(this);
        return pendingInvites;
    }

    public boolean hasPendingInviteFromAccount(Account sendingAccount) {
        ArrayList<Invite> pendingInvites = getAllPendingInvites();
        for (Invite invite : pendingInvites) {
            Game game = invite.getGame();
            for (Player player : game.getPlayers()) {
                String sendingAccountUsername = sendingAccount.getUsername();
                String playerUsername = player.getAccount().getUsername();

                if (sendingAccountUsername.equals(playerUsername)) {
                    if (player.getPlayerStatus().equals("challenger")) {
                        // Check if the player was the creator of the game
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
