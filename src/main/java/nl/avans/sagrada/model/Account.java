package nl.avans.sagrada.model;

import java.util.ArrayList;
import nl.avans.sagrada.dao.InviteDao;
import nl.avans.sagrada.dao.PlayerDao;
import nl.avans.sagrada.model.enumerations.AccountStatus;

public class Account {
    private AccountStatus accountStatus;
    private String username;
    private String password;
    private ArrayList<Player> players;
    private ArrayList<Invite> pendingInvites;

    /**
     * Empty constructor, initializes private ArrayLists.
     */
    public Account() {
        players = new ArrayList<>();
        pendingInvites = new ArrayList<>();
    }

    /**
     * Full constructor, initializes all instance variables.
     *
     * @param username the username of this account.
     * @param password the password of this account.
     */
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        players = new ArrayList<>();
        pendingInvites = new ArrayList<>();
    }

    /**
     * This method will return the username of this account. The username is a unique identifier for
     * this account. The username is visible to other accounts in the GUI.
     *
     * @return The username of this account.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Registers the username of this account. The username is a unique identifier for this
     * account.
     *
     * @param username The username of this account.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method will return the password of this account. The password combined with a username
     * is used as authentication for this account
     *
     * @return The password of this Account.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Registers the password of this account. The password combined with a username is used as
     * authentication for this account.
     *
     * @param password The password of this account.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method will return a list of pending invites of this account. The list is used to view
     * pending invites in the GUI. The pending invites will be accessed from the database using the
     * InviteDao and this account object.
     *
     * @return The list of pending invites.
     * @see Invite
     */
    public ArrayList<Invite> getPendingInvites() {
        InviteDao inviteDao = new InviteDao();
        pendingInvites = inviteDao.getAllPendingInvitesOfAccount(this);
        return pendingInvites;
    }

    /**
     * Registers a list of pending invites of this account.
     *
     * @param pendingInvites The list of pending invites of this account.
     * @see Invite
     */
    public void setPendingInvites(ArrayList<Invite> pendingInvites) {
        this.pendingInvites = pendingInvites;
    }

    /**
     * This method will return a list of Players of this account. The Players will be accessed from
     * the database using the PlayerDao and this account object.
     *
     * @return The list of Players.
     * @see Player
     */
    public ArrayList<Player> getPlayers() {
        PlayerDao playerDao = new PlayerDao();
        players = playerDao.getPlayersOfAccount(this);
        return players;
    }

    /**
     * Registers a list of Players of this account.
     *
     * @param players The list of Players.
     * @see Player
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * This method will return a list of Games of this account. The Games will be set using the
     * players list.
     *
     * @return The list of Games.
     * @see Player#getGame()
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
     * This method will return a list of active Games of this account. It uses the getGames method
     * to receive all Games and checks each game for if the Game is active.
     *
     * @return The list of active Games.
     * @see Game#isActive()
     */
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

    /**
     * This method will return true when this account has already received an invite from another
     * account, which is given as a parameter. The method wil look through all pending invites and
     * get the sending account's username. If the username of the sending account equals the
     * username of the account given in the parameter, the method will return True. If the
     * username's of all pending invites didn't match the method will return False.
     *
     * @param sendingAccount The account sending the invite.
     * @return True when this account already has a invite from a other account.
     * @see Account#getGames()
     */
    public boolean hasPendingInviteFromAccount(Account sendingAccount) {
        ArrayList<Invite> pendingInvites = getPendingInvites();
        for (Invite invite : pendingInvites) {
            Game game = invite.getGame();
            for (Player player : game.getPlayers()) {
                String sendingAccountUsername = sendingAccount.getUsername();
                String playerUsername = player.getAccount().getUsername();

                if (sendingAccountUsername.equals(playerUsername)) {
                    if (player.getPlayerStatus().equals("uitdager")) {
                        // Check if the player was the creator of the game
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Sets the current account status of the account
     * The account status could also be referred to as location
     * @param accountStatus
     */
    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }
    
    /**
     * Returns the account status
     * @return accountStatus
     */
    public AccountStatus getAccountStatus() {
        return accountStatus;
    }
}
