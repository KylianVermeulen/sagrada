package nl.avans.sagrada.model;

public class Invite {
    private Account invitedAccount;
    private Game game;
    private boolean accepted;
    private boolean denied;
    private Player player;

    public Invite() {
        accepted = false;
        denied = false;
    }

    /**
     * Accepts the invite
     */
    public void acceptInvite() {
        accepted = true;
        denied = false;
    }

    /**
     * Denies the invite request
     */
    public void denyInvite() {
        accepted = false;
        denied = true;
    }

    /**
     * Gets the status of the invite as a String
     *
     * @return String
     */
    public String getStatus() {
        if (accepted) {
            return "geaccepteerd";
        } else if (denied) {
            return "geweigerd";
        } else {
            return "uitgedaagde";
        }
    }

    /**
     * Checks if the invite has not been responded to
     *
     * @return boolean
     */
    public boolean isPending() {
        return (!accepted && !denied);
    }

    /**
     * Get the account that is invited
     *
     * @return Account
     */
    public Account getInvitedAccount() {
        return invitedAccount;
    }

    /**
     * Set the account that the invite is for
     */
    public void setInvitedAccount(Account account) {
        invitedAccount = account;
    }

    /**
     * Get the game that the invite is for
     *
     * @return Game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Set the game that is connected to the invite
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Gets the player that the invite is for
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets a player for who the invite is
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}
