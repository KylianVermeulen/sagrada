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
        players = new ArrayList<Player>();
        pendingInvites = new ArrayList<Invite>();
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
        players = new ArrayList<Player>();
        pendingInvites = new ArrayList<Invite>();
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
     * Checks if the account has a pending invite of the 
     * account that will send the invite
     * If there is a pending invite we return true
     * @param sendingAccount
     * @return boolean
     */
    public boolean hasPendingInviteFromAccount(Account sendingAccount) {
        ArrayList<Invite> pendingInvites = getAllPendingInvites();
        for (Invite invite: pendingInvites) {            
            Game game = invite.getGame();
            for (Player player: game.getPlayers()) {
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
    
    /**
     * Get all the active games of a account
     * @return ArrayList<Game>
     */
    public ArrayList<Game> getActiveGames() {
        ArrayList<Game> games = getGames();
        ArrayList<Game> activeGames = new ArrayList<>();
        
        for (Game game: games) {
            if (game.isActive()) {
                activeGames.add(game);
            }
        }
        return activeGames;        
    }
}
