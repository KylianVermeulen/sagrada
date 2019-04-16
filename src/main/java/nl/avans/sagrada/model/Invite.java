package nl.avans.sagrada.model;

public class Invite {
    private Player sendedPlayer;
    private Account invitedAccount;
    private Game game;
    private boolean accepted;
    private boolean denied;
    
    public Invite() {
        accepted = false;
        denied = false;
    }
    
    /**
     * Sets the player that has send the invite
     * @param player
     */
    public void setSendedPlayer(Player player) {
        sendedPlayer = player;
    }

    
    /**
     * Set the account that the invite is for
     * @param account
     */
    public void setInvitedAccount(Account account) {
        invitedAccount = account;
    }
    
    /**
     * Set the game that is connected to the invite
     * @param game
     */
    public void setGame(Game game) {
        this.game = game;
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
     * Checks if the invite has not been responded to
     * @return boolean
     */
    public boolean isPending() {
        return (!accepted && !denied);
    }
    
    /**
     * Gets the player that has sended the invite
     * @return Player
     */
    public Player getSendedPlayer() {
        return sendedPlayer;
    }
}
